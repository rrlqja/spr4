package song.spring4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import song.spring4.userdetails.UserDetailsImpl;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

//    @ResponseBody
//    @GetMapping
//    public List<SimpleGrantedAuthority> getAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        List<SimpleGrantedAuthority> authorities = userDetails.getAuthorities().stream().map(
//                role -> new SimpleGrantedAuthority(role.getAuthority().toString())
//        ).toList();
//
//        return authorities;
//    }

    @ResponseBody
    @GetMapping
    public void getAdmin() {
        log.info("getAdmin");

    }
}
