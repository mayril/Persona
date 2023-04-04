package com.ssafy.project.api.controller;

import com.ssafy.project.api.service.VideoService;
import com.ssafy.project.common.constant.Msg;
import com.ssafy.project.common.db.dto.common.ResponseDTO;
import com.ssafy.project.common.db.dto.request.VideoCreateReqDTO;
import com.ssafy.project.common.db.dto.request.VideoDeleteReqDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Log4j2
@RestController
@Api(tags = {"비디오 API"})
@RequiredArgsConstructor
@RequestMapping(value = "/video")
public class VideoController {

    private final VideoService videoService;

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    @ApiOperation(value = "비디오 저장")
    public ResponseEntity<ResponseDTO> videoSave(@ModelAttribute VideoCreateReqDTO videoCreateReqDTO) throws FileNotFoundException {

        if(!videoCreateReqDTO.getVideoFile().getContentType().startsWith("video")) {
            return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.UNSUPPORTED_MEDIA_TYPE, Msg.FAIL_CREATE));
        }

        videoService.saveVideo(videoCreateReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_CREATE));
    }

    @DeleteMapping
    @ApiOperation(value = "비디오 삭제")
    public ResponseEntity<ResponseDTO> videoDelete(@RequestParam VideoDeleteReqDTO videoDeleteReqDTO){
        videoService.deleteVideo(videoDeleteReqDTO);
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_DELETE));
    }

    @GetMapping
    @ApiOperation(value = "내 비디오 조회")
    public ResponseEntity<ResponseDTO> videoMyList(@RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_READ, videoService.findAllVideo(page)));
    }
}
