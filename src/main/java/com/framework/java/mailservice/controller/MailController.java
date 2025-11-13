package com.framework.java.mailservice.controller;

import com.framework.java.mailservice.dto.UsersDto;
import com.framework.java.mailservice.entity.UsersEmail;
import com.framework.java.mailservice.service.UsersEmailService;
import com.framework.java.mailservice.utils.MailUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sendEmail")
public class MailController {

    private static final String SENDER_EMAIL = "jyotidangal060@gmail.com";

    @Autowired
    private UsersEmailService usersEmailService;

    // ✅ Load email page with both DTOs bound
    @GetMapping
    public String inputPage(Model model) {
        // Add DTOs for both forms so Thymeleaf can bind fields properly
        model.addAttribute("singleUsersDto", new UsersDto());
        model.addAttribute("allUsersDto", new UsersDto());

        return "sendEmail/sendMail"; // must match your HTML file name
    }

    // ✅ Send email to a single recipient
    @PostMapping("/send-email")
    public String sendEmail(@ModelAttribute("singleUsersDto") UsersDto singleUsersDto,
                            RedirectAttributes redirectAttributes) {

        String recipient = singleUsersDto.getEmail();
        String subject = singleUsersDto.getSubject();
        String description = singleUsersDto.getDescription();

        if (recipient.equalsIgnoreCase(SENDER_EMAIL)) {
            redirectAttributes.addFlashAttribute("message", "Sender and recipient email cannot be the same!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/sendEmail";
        }

        UsersEmail existingUser = usersEmailService.getUserByEmail(recipient);
        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("message", "The entered email does not exist in the database!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/sendEmail";
        }

        try {
            MailUtils.sendEmail(recipient, subject, description);
            redirectAttributes.addFlashAttribute("message", "Email sent successfully to " + recipient);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (MessagingException e) {
            redirectAttributes.addFlashAttribute("message", "Failed to send email: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/sendEmail";
    }

    // ✅ Send email to all users
    @PostMapping("/send-to-all")
    public String sendEmailToAll(@ModelAttribute("allUsersDto") UsersDto allUsersDto,
                                 RedirectAttributes redirectAttributes) {

        String subject = allUsersDto.getSubject();
        String description = allUsersDto.getDescription();

        try {
            List<UsersEmail> users = usersEmailService.getAllEmails();

            if (users == null || users.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "No recipients found in the database.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
                return "redirect:/sendEmail";
            }

            int sentCount = 0;
            for (UsersEmail user : users) {
                String recipient = user.getEmail();
                if (!recipient.equalsIgnoreCase(SENDER_EMAIL)) {
                    MailUtils.sendEmail(recipient, subject, description);
                    sentCount++;
                }
            }

            redirectAttributes.addFlashAttribute("message", "Emails sent successfully to " + sentCount + " users!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        } catch (MessagingException e) {
            redirectAttributes.addFlashAttribute("message", "Failed to send emails: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/sendEmail";
    }
}
