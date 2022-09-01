package com.example.demo.controller;

import com.example.demo.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
    //모든 사람이 접근할 수 있는 경로
    @GetMapping("/all")
    public void exAll(){
        log.info("exAll-----------");
    }
    //로그인한 사람만 접근할 수 있는 경로
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal AuthMemberDTO dto){
        log.info("exMember-----------");
        log.info("-----------");
        log.info(dto);

    }
    //관리자만 접근 할 수 있는 경로
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin-----------");
    }
    
}
