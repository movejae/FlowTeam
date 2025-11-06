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

        //this attribute use when Set FrontEnd const value.
        model.addAttribute("extensionNameMaxLength", ExtensionConstants.EXTENSION_MAX_LENGTH);
        model.addAttribute("extensionMaxCount", ExtensionConstants.MAX_CUSTOM_EXTENSIONS);

        return "index";
    }
}
