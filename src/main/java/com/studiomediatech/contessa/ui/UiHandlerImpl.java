package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.domain.Entry;

import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;

import org.springframework.util.StringUtils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


public class UiHandlerImpl implements UiHandler {

    private final ContentsService contentsService;
    private final BuildProperties buildProps;
    private final GitProperties gitProps;

    public UiHandlerImpl(ContentsService contentsService, BuildProperties buildProps, GitProperties gitProps) {

        this.contentsService = contentsService;
        this.buildProps = buildProps;
        this.gitProps = gitProps;
    }

    @Override
    public Entry handle(UploadRequest command) {

        return log_returns(contentsService.addMediaContent(command.filename, command.payload));
    }


    @Override
    public Optional<Entry> handle(ContentRequest command) {

        Optional<Entry> entry = Optional.empty();

        if (StringUtils.hasText(command.name)) {
            entry = contentsService.getMediaContentForName(command.name);
        }

        if (StringUtils.hasText(command.identifier)) {
            entry = contentsService.getMediaContentForIdentifier(command.identifier);
        }

        return log_returns(entry);
    }


    @Override
    public Map<String, Object> handle(InfoRequest request) {

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("version", buildProps.getVersion());
        map.put("commit", gitProps.getCommitId());

        map.put("entries", contentsService.getMediaContentCount());

        map.put("web", ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString());
        map.put("rest", ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1").build().toUriString());

        return map;
    }
}
