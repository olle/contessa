Contessa
========

A very simple media content service, built on **Spring Boot**. Provides a quick
and easy way to manage the storage, upload and access to media and assets, in a
situation where a real [CDN][1] may not (yet) be a viable solution.

  [1]: https://en.wikipedia.org/wiki/Content_delivery_network


Quickstart
----------

Contessa is packaged as a fat-jar, and can be started by simply executing it:

```
  $ java -Dcontessa.base-dir=data/ -jar contessa.jar
```

The `base-dir` configuration property is required, and starting up won't be
possible without setting it.

You can now browse  `http://localhost:8080` and you should see some information
about Contessa, the version, and commit as well as the current content entry
count.

There are two main user interfaces on top of HTTP; a straight-forward web, and
the a REST-ful API mapped to `http://localhost:8080/api/v1`. The REST-ful API
does not serve the actual content, but rather provides content entries as
`application/json` resources.

All content entry data is served from the web UI.

### Adding content via the web UI

Users will typically upload some named content, and capture the response, which
will be the unique content entry URI.

```
  $ curl --request POST \
         --header "content-type: application/octet-stream" \
         --data-binary "@some.gif" \
         http://localhost:8080/some.gif
  http://localhost:8080/ede542cb-f8ec1d4.gif
```

The returned URI is calculated to be unique, both with regards to the data as
well as the name.

If we request the URI we can see that Contessa will return a cache-control
header, which by default uses a `max-age` property of 100 days.

```
  $ curl -I http://localhost:8080/ede542cb-f8ec1d4.gif
  HTTP/1.1 200
  X-Application-Context: contessa
  ETag: "ede542cb-f8ec1d4"
  Cache-Control: max-age=8640000, public, immutable
  Content-Type: image/gif
  Content-Length: 27566
  Date: Sat, 13 Jan 2018 15:34:21 GMT
```

Design &amp; Idea
-----------------

The main intent is to have a simple content service, with a strict and minimal
API surface. Users should be able to submit and retrieve, content files in some
appropriate ways.

Contessa will provide a very rich configurable out-of-the-box solution, that
offers a sensible and adaptive solution to business hosting of media content.

### Extensible structure

The design goal is to provide a separation between the two most important
sub-systems or _tiers_ - the user interface or API and the storage backend or
data repository.

#### Configurable data repository

Using a simple set of configurations, the storage of data can be controlled, and
easily set up for local filestorage, SQL or NoSQL storage.

Each storage comes with a different set of capabilities and Contessa allows for
an adaptable solution, that suites many different business goals.

#### Optional user interfaces

Accessing or uploading data can be made in several different ways. From plain
HTTP or web to REST-ful APIs, file-system polling or message bus brokers, the
feature-set is rich and very capable. This puts Contessa in a compatibility
level of its own.


Development &amp; Stack
-----------------------

The current aim and goal of Contessa is to ensure the best possible capabilities
for a meda content storage - but also, to stay within the boundaries of a very
well understood and capable technical stack.

Building on [Java][2] and [Spring Boot][3], currently mean that we can leverage
the knowledge and interest of a whole lof of developers. We believe these
developers to also be great ambassadors for Contessa, if we can provide them
with idiomatic, transparent and well thought out code.

  [2]: https://java.oracle.com
  [3]: http://projects.spring.io/spring-boot/

### Building &amp; testing

Anyone should be able to join in, and as the project evolves we will provide
more specific information. At the moment running the project should be as easy
as starting the project using the shortcut in the `Makefile`:

    > make start

There will be more documentation evolving inside the `docs/` folder.

Happy hacking!
