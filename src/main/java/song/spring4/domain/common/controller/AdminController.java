package song.spring4.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import song.spring4.security.pricipal.UserPrincipal;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @ResponseBody
    @GetMapping
    public List<SimpleGrantedAuthority> getAdmin(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        List<SimpleGrantedAuthority> authorities = userPrincipal.getAuthorities().stream().map(
                role -> new SimpleGrantedAuthority(role.getAuthority().toString())
        ).toList();

        return authorities;
    }

}
