package com.flow.folwteamtest.controller;

import com.flow.folwteamtest.common.ExtensionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewController {

    /**
     * 메인 페이지
     */
    @GetMapping("/")
    public String index(Model model) {
        log.info("Main page requested");

        // 프론트엔드에서 사용할 상수 전달
        model.addAttribute("maxLength", ExtensionConstants.EXTENSION_MAX_LENGTH);
        model.addAttribute("maxCount", ExtensionConstants.MAX_CUSTOM_EXTENSIONS);

        return "index";
    }
}
