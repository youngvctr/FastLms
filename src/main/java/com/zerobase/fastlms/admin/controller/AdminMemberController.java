package com.zerobase.fastlms.admin.controller;


import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.member.dto.UserLoginHistoryDto;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {

    private final MemberService memberService;
    private final HistoryService historyService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {

        parameter.init();
        List<MemberDto> members = memberService.list(parameter);

        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {

        parameter.init();

        MemberDto member = memberService.detail(parameter.getUserId());

        List<UserLoginHistoryDto> userLoginHistoryDto = historyService.getUserLoginHistoryDto(parameter.getUserId());

        model.addAttribute("member", member);

        List<String> historyList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userLoginHistoryDto.size(); i++) {
            int num = i + 1;
            UserLoginHistoryDto temp = userLoginHistoryDto.get(i);
            sb.append("[")
                    .append(num)
                    .append("] [IP] ")
                    .append(temp.getIp())
                    .append(" [UserAgent] ")
                    .append(temp.getUserAgent())
                    .append(" [LoginDateTime] ")
                    .append(temp.getLoginDateTime());
            historyList.add(new String(sb));
            sb.setLength(0);
        }

        model.addAttribute("loginHistory", historyList);

        return "admin/member/detail";
    }

    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {


        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }


    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {


        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
}
