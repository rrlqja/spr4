package song.spring4.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import song.spring4.domain.user.dto.*;
import song.spring4.security.pricipal.UserPrincipal;
import song.spring4.domain.user.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUser(@AuthenticationPrincipal UserPrincipal userPrincipal,
                          Model model) {
        ResponseUserDto user = userService.findUserById(userPrincipal.getId());

        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/updateUsername")
    public String getUsername(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @ModelAttribute UpdateUsernameDto updateUsernameDto) {
        updateUsernameDto.setUsername(userPrincipal.getUsername());

        return "user/updateUsername";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateUsername")
    public void postUpdateUsername(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                   @Valid @ModelAttribute UpdateUsernameDto updateUsernameDto) {
        userService.updateUsername(userPrincipal.getId(), updateUsernameDto.getUsername());
    }

    @GetMapping("/updatePassword")
    public String getPassword(@ModelAttribute UpdatePasswordDto updatePasswordDto) {

        return "user/updatePassword";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updatePassword")
    public void postUpdatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                   @Valid @ModelAttribute UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(userPrincipal.getId(), updatePasswordDto.getOriginalPassword(), updatePasswordDto.getNewPassword());
    }

    @GetMapping("/updateName")
    public String getName(@AuthenticationPrincipal UserPrincipal userPrincipal,
                          @ModelAttribute UpdateNameDto updateNameDto) {
        updateNameDto.setName(userPrincipal.getName());

        return "user/updateName";
    }

    @PostMapping("/updateName")
    public String postUpdateName(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                 @Valid @ModelAttribute UpdateNameDto updateNameDto) {
        userService.updateName(userPrincipal.getId(), updateNameDto.getName());

        return "redirect:/user";
    }

    @GetMapping("/updateEmail")
    public String getEmail(@AuthenticationPrincipal UserPrincipal userPrincipal,
                           @ModelAttribute UpdateEmailDto updateEmailDto) {
        updateEmailDto.setEmail(userPrincipal.getEmail());

        return "user/updateEmail";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateEmail")
    public void postUpdateEmail(@AuthenticationPrincipal UserPrincipal userPrincipals,
                                @Valid @ModelAttribute UpdateEmailDto updateEmailDto) {
        userService.updateEmail(userPrincipals.getId(), updateEmailDto.getEmail());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/validateUsername")
    public void postValidateUsername(@RequestParam("username") String username) {
        userService.validateUsername(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/deleteUser")
    public void postDeleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               HttpServletRequest request) {
        userService.deleteUserById(userPrincipal.getId());

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
