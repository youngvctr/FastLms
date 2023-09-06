package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.service.HistoryService;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class UserAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
    private final MemberService memberService;
    private final HistoryService historyService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        Object oPrincipal = auth.getPrincipal();
        UserDetails userDetails = (UserDetails) oPrincipal;

        String userId = userDetails.getUsername();
        String ip = getIpFromHeaderOrLocalhost(new String(), req);
        String userAgent = req.getHeader("User-Agent");
        Optional<LoginHistory> loginHistory = Optional.of(LoginHistory.builder()
                .userId(userId)
                .ip(ip)
                .userAgent(userAgent)
                .build());

        memberService.updateLastLoginDateTime(userId, LocalDateTime.now());
        historyService.saveLogOnLogin(loginHistory.get());

        log.info("Login Information [ " + LocalDate.now() + " ]");
        log.info(" Id = " + loginHistory.get().getUserId() +
                ", Ip = " + loginHistory.get().getIp() +
                ", User Agent = " + userAgent +
                ", Last Login = " + loginHistory.get().getLoginDateTime().toLocalDate());

        super.onAuthenticationSuccess(req, res, auth);
    }

    String getIpFromHeaderOrLocalhost(String ip, HttpServletRequest req) throws UnknownHostException {
        StringBuilder tempIp = new StringBuilder();
        for (String header : IP_HEADER_CANDIDATES) {
            tempIp.append(req.getHeader(header));
            if (!tempIp.toString().equals("null") && tempIp.length() != 0) {
                ip = new String(tempIp);
                break;
            }
            tempIp.setLength(0);
        }

        if (tempIp.length() == 0) {
            String address = req.getRemoteAddr();
            if (address.equals("0:0:0:0:0:0:0:1") || address.equals("127.0.0.1")) {
                InetAddress resAddress = InetAddress.getLocalHost();
                ip = resAddress.getHostAddress();
            } else {
                ip = address;
            }
        }
        return ip;
    }
}
