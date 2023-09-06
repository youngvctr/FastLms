package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdminBannerController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model) {

        List<BannerDto> banners = bannerService.list();
        model.addAttribute("list", banners);

        return "admin/banner/list";
    }

    @GetMapping("/admin/banner/add.do")
    public String add() {
        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addSubmit(MultipartFile file, BannerDto bannerDto) {

        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            String baseLocalPath = "C:/db/images";
            String baseUrlPath = "/static/images";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bannerDto.setImagePath(urlFilename);
        bannerDto.setLinkUrl(urlFilename);

        bannerService.add(bannerDto);
        return "redirect:/admin/banner/list.do";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request, CourseInput parameter) {

        boolean result = bannerService.del(parameter.getIdList());

        return "redirect:/admin/banner/list.do";
    }
}
