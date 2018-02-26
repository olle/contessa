package com.studiomediatech.contessa.app.autoconfigure.hacks;

/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties.Strategy;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.io.Resource;

import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import org.springframework.validation.Validator;

import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link EnableWebMvc Web MVC}.
 *
 * @author  Phillip Webb
 * @author  Dave Syer
 * @author  Andy Wilkinson
 * @author  Sébastien Deleuze
 * @author  Eddú Meléndez
 * @author  Stephane Nicoll
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurerAdapter.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, ValidationAutoConfiguration.class })
public class CustomWebMvcConfiguration {

    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_SUFFIX = "";

    interface ResourceHandlerRegistrationCustomizer {

        void customize(ResourceHandlerRegistration registration);
    }

    // Defined as a nested config to ensure WebMvcConfigurerAdapter is not read when not
    // on the classpath
    @Configuration
    @Import(EnableWebMvcConfiguration.class)
    @EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
    public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {

        private final WebMvcProperties mvcProperties;
        private final ListableBeanFactory beanFactory;
        private final HttpMessageConverters messageConverters;
        final ResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer;

        public WebMvcAutoConfigurationAdapter(WebMvcProperties mvcProperties, ListableBeanFactory beanFactory,
            @Lazy HttpMessageConverters messageConverters,
            ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider) {

            this.mvcProperties = mvcProperties;
            this.beanFactory = beanFactory;
            this.messageConverters = messageConverters;
            this.resourceHandlerRegistrationCustomizer =
                resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

            converters.addAll(this.messageConverters.getConverters());
        }


        @Override
        public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

            Long timeout = this.mvcProperties.getAsync().getRequestTimeout();

            if (timeout != null) {
                configurer.setDefaultTimeout(timeout);
            }
        }


        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

            Map<String, MediaType> mediaTypes = this.mvcProperties.getMediaTypes();

            for (Entry<String, MediaType> mediaType : mediaTypes.entrySet()) {
                configurer.mediaType(mediaType.getKey(), mediaType.getValue());
            }
        }


//        @Bean
//        @ConditionalOnMissingBean
//        public InternalResourceViewResolver defaultViewResolver() {
//
//            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//            resolver.setPrefix(this.mvcProperties.getView().getPrefix());
//            resolver.setSuffix(this.mvcProperties.getView().getSuffix());
//
//            return resolver;
//        }

//        @Bean
//        @ConditionalOnBean(View.class)
//        @ConditionalOnMissingBean
//        public BeanNameViewResolver beanNameViewResolver() {
//
//            BeanNameViewResolver resolver = new BeanNameViewResolver();
//            resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
//
//            return resolver;
//        }

//        @Bean
//        @ConditionalOnBean(ViewResolver.class)
//        @ConditionalOnMissingBean(name = "viewResolver", value = ContentNegotiatingViewResolver.class)
//        public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
//
//            ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//            resolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
//
//            // ContentNegotiatingViewResolver uses all the other view resolvers to locate
//            // a view so it should have a high precedence
//            resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//            return resolver;
//        }

//        @Bean
//        @ConditionalOnMissingBean
//        @ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
//        public LocaleResolver localeResolver() {
//
//            if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
//                return new FixedLocaleResolver(this.mvcProperties.getLocale());
//            }
//
//            AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//            localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
//
//            return localeResolver;
//        }

//        @Bean
//        @ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")
//        public Formatter<Date> dateFormatter() {
//
//            return new DateFormatter(this.mvcProperties.getDateFormat());
//        }

//        @Override
//        public MessageCodesResolver getMessageCodesResolver() {
//
//            if (this.mvcProperties.getMessageCodesResolverFormat() != null) {
//                DefaultMessageCodesResolver resolver = new DefaultMessageCodesResolver();
//                resolver.setMessageCodeFormatter(this.mvcProperties.getMessageCodesResolverFormat());
//
//                return resolver;
//            }
//
//            return null;
//        }

        @Override
        public void addFormatters(FormatterRegistry registry) {

            for (Converter<?, ?> converter : getBeansOfType(Converter.class)) {
                registry.addConverter(converter);
            }

            for (GenericConverter converter : getBeansOfType(GenericConverter.class)) {
                registry.addConverter(converter);
            }

            for (Formatter<?> formatter : getBeansOfType(Formatter.class)) {
                registry.addFormatter(formatter);
            }
        }


        private <T> Collection<T> getBeansOfType(Class<T> type) {

            return this.beanFactory.getBeansOfType(type).values();
        }
    }

    /**
     * Configuration equivalent to {@code @EnableWebMvc}.
     */
    @Configuration
    public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {

        private final WebMvcProperties mvcProperties;

        private final ListableBeanFactory beanFactory;

        private final WebMvcRegistrations mvcRegistrations;

        public EnableWebMvcConfiguration(ObjectProvider<WebMvcProperties> mvcPropertiesProvider,
            ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ListableBeanFactory beanFactory) {

            this.mvcProperties = mvcPropertiesProvider.getIfAvailable();
            this.mvcRegistrations = mvcRegistrationsProvider.getIfUnique();
            this.beanFactory = beanFactory;
        }

        @Bean
        @Override
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

            RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
            adapter.setIgnoreDefaultModelOnRedirect(this.mvcProperties == null
                    ? true : this.mvcProperties.isIgnoreDefaultModelOnRedirect());

            return adapter;
        }


        @Override
        protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {

            if (this.mvcRegistrations != null && this.mvcRegistrations.getRequestMappingHandlerAdapter() != null) {
                return this.mvcRegistrations.getRequestMappingHandlerAdapter();
            }

            return super.createRequestMappingHandlerAdapter();
        }


        @Bean
        @Primary
        @Override
        public RequestMappingHandlerMapping requestMappingHandlerMapping() {

            // Must be @Primary for MvcUriComponentsBuilder to work
            return super.requestMappingHandlerMapping();
        }


        @Bean
        @Override
        public Validator mvcValidator() {

            if (!ClassUtils.isPresent("javax.validation.Validator", getClass().getClassLoader())) {
                return super.mvcValidator();
            }

            return WebMvcValidator.get(getApplicationContext(), getValidator());
        }


        @Override
        protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {

            if (this.mvcRegistrations != null && this.mvcRegistrations.getRequestMappingHandlerMapping() != null) {
                return this.mvcRegistrations.getRequestMappingHandlerMapping();
            }

            return super.createRequestMappingHandlerMapping();
        }


        @Override
        protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {

            try {
                return this.beanFactory.getBean(ConfigurableWebBindingInitializer.class);
            } catch (NoSuchBeanDefinitionException ex) {
                return super.getConfigurableWebBindingInitializer();
            }
        }


        @Override
        protected ExceptionHandlerExceptionResolver createExceptionHandlerExceptionResolver() {

            if (this.mvcRegistrations != null
                    && this.mvcRegistrations.getExceptionHandlerExceptionResolver() != null) {
                return this.mvcRegistrations.getExceptionHandlerExceptionResolver();
            }

            return super.createExceptionHandlerExceptionResolver();
        }


        @Override
        protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

            super.configureHandlerExceptionResolvers(exceptionResolvers);

            if (exceptionResolvers.isEmpty()) {
                addDefaultHandlerExceptionResolvers(exceptionResolvers);
            }

            if (this.mvcProperties.isLogResolvedException()) {
                for (HandlerExceptionResolver resolver : exceptionResolvers) {
                    if (resolver instanceof AbstractHandlerExceptionResolver) {
                        ((AbstractHandlerExceptionResolver) resolver).setWarnLogCategory(resolver.getClass()
                            .getName());
                    }
                }
            }
        }


        @Bean
        @Override
        public ContentNegotiationManager mvcContentNegotiationManager() {

            ContentNegotiationManager manager = super.mvcContentNegotiationManager();
            List<ContentNegotiationStrategy> strategies = manager.getStrategies();
            ListIterator<ContentNegotiationStrategy> iterator = strategies.listIterator();

            while (iterator.hasNext()) {
                ContentNegotiationStrategy strategy = iterator.next();

                if (strategy instanceof PathExtensionContentNegotiationStrategy) {
                    iterator.set(new OptionalPathExtensionContentNegotiationStrategy(strategy));
                }
            }

            return manager;
        }
    }

    @Configuration
    @ConditionalOnEnabledResourceChain
    static class ResourceChainCustomizerConfiguration {

        @Bean
        public ResourceChainResourceHandlerRegistrationCustomizer resourceHandlerRegistrationCustomizer() {

            return new ResourceChainResourceHandlerRegistrationCustomizer();
        }
    }

    private static class ResourceChainResourceHandlerRegistrationCustomizer
        implements ResourceHandlerRegistrationCustomizer {

        @Autowired
        private ResourceProperties resourceProperties = new ResourceProperties();

        @Override
        public void customize(ResourceHandlerRegistration registration) {

            ResourceProperties.Chain properties = this.resourceProperties.getChain();
            configureResourceChain(properties, registration.resourceChain(properties.isCache()));
        }


        private void configureResourceChain(ResourceProperties.Chain properties, ResourceChainRegistration chain) {

            Strategy strategy = properties.getStrategy();

            if (strategy.getFixed().isEnabled() || strategy.getContent().isEnabled()) {
                chain.addResolver(getVersionResourceResolver(strategy));
            }

            if (properties.isGzipped()) {
                chain.addResolver(new GzipResourceResolver());
            }

            if (properties.isHtmlApplicationCache()) {
                chain.addTransformer(new AppCacheManifestTransformer());
            }
        }


        private ResourceResolver getVersionResourceResolver(ResourceProperties.Strategy properties) {

            VersionResourceResolver resolver = new VersionResourceResolver();

            if (properties.getFixed().isEnabled()) {
                String version = properties.getFixed().getVersion();
                String[] paths = properties.getFixed().getPaths();
                resolver.addFixedVersionStrategy(version, paths);
            }

            if (properties.getContent().isEnabled()) {
                String[] paths = properties.getContent().getPaths();
                resolver.addContentVersionStrategy(paths);
            }

            return resolver;
        }
    }

    static final class WelcomePageHandlerMapping extends AbstractUrlHandlerMapping {

        private static final Log logger = LogFactory.getLog(WelcomePageHandlerMapping.class);

        private WelcomePageHandlerMapping(Resource welcomePage, String staticPathPattern) {

            if (welcomePage != null && "/**".equals(staticPathPattern)) {
                logger.info("Adding welcome page: " + welcomePage);

                ParameterizableViewController controller = new ParameterizableViewController();
                controller.setViewName("forward:index.html");
                setRootHandler(controller);
                setOrder(0);
            }
        }

        @Override
        public Object getHandlerInternal(HttpServletRequest request) throws Exception {

            for (MediaType mediaType : getAcceptedMediaTypes(request)) {
                if (mediaType.includes(MediaType.TEXT_HTML)) {
                    return super.getHandlerInternal(request);
                }
            }

            return null;
        }


        private List<MediaType> getAcceptedMediaTypes(HttpServletRequest request) {

            String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

            return MediaType.parseMediaTypes(StringUtils.hasText(acceptHeader) ? acceptHeader : "*/*");
        }
    }

    /**
     * Decorator to make {@link PathExtensionContentNegotiationStrategy} optional depending on a request attribute.
     */
    static class OptionalPathExtensionContentNegotiationStrategy implements ContentNegotiationStrategy {

        private static final String SKIP_ATTRIBUTE = PathExtensionContentNegotiationStrategy.class.getName() + ".SKIP";

        private final ContentNegotiationStrategy delegate;

        OptionalPathExtensionContentNegotiationStrategy(ContentNegotiationStrategy delegate) {

            this.delegate = delegate;
        }

        @Override
        public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest)
            throws HttpMediaTypeNotAcceptableException {

            Object skip = webRequest.getAttribute(SKIP_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

            if (skip != null && Boolean.parseBoolean(skip.toString())) {
                return Collections.emptyList();
            }

            return this.delegate.resolveMediaTypes(webRequest);
        }
    }
}
