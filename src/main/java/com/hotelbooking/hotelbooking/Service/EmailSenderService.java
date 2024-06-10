package com.hotelbooking.hotelbooking.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Class.BookingDetail;
import com.hotelbooking.hotelbooking.Config.MailConfig;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailSenderService {
    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private BookingHotelService hotelBookingService;

    public void sendEmailToBookingPerson(String bookingId) {
        JavaMailSender mailSender = mailConfig.MailConfig();
        MimeMessage message = mailSender.createMimeMessage();
        HotelBooking booking = hotelBookingService.getBookingById(bookingId);
        BookingDetail detail = booking.getBookingDetail().get(0);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String subject = "Thank you for your booking. Here are your booking details";
            String body = buildBookingConfirmationEmail(detail);

            helper.setFrom("duongbaolam2214@gmail.com");
            helper.setTo(detail.getEmail());
            helper.setSubject(subject);
            helper.setText(body, true); // Set 'true' for HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String buildBookingConfirmationEmail(BookingDetail detail) {
        
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("body {font-family: Arial, sans-serif;}")
                .append(".container {width: 80%; margin: 0 auto; padding: 20px; border: 1px solid #ccc;}")
                .append(".header {background-color: #f7f7f7; padding: 10px 0; text-align: center;}")
                .append(".content {padding: 20px;}")
                .append(".footer {background-color: #f7f7f7; padding: 10px 0; text-align: center;}")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class='container'>")
                .append("<div class='header'>")
                .append("<h1>Booking Confirmation</h1>")
                .append("</div>")
                .append("<div class='content'>")
                .append("<p>Dear " + detail.getFirstName() + " " + detail.getLastName() + ",</p>")
                .append("<p>Thank you for your booking. Here are your booking details:</p>")
                .append("<ul>")
                .append("<li><strong>Number of Guests:</strong> " + detail.getNumberOfGuests() + "</li>")
                .append("<li><strong>Check-in Date:</strong> " + detail.getArrivalDate() + "</li>")
                .append("<li><strong>Check-in Time:</strong> " + detail.getArrivalTime() + "</li>")
                .append("<li><strong>Payment Status:</strong> " + (detail.isPayment() ? "Paid" : "Pending") + "</li>")
                .append("</ul>")
                .append("</div>")
                .append("<div class='footer'>")
                .append("<p>If you have any questions, feel free to contact us at duongbaolam2214@gmail.com.</p>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return emailContent.toString();
    }
}
