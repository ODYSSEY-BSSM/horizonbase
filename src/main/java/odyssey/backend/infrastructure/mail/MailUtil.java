package odyssey.backend.infrastructure.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public void sendMimeMessage(String email, String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(email);

            mimeMessageHelper.setSubject("호라이즌 인증 코드입니다");

            String content = """
                    <!DOCTYPE html>
                        <html lang="ko">
                        <head>
                          <meta charset="UTF-8">
                          <title>호라이즌 인증 코드</title>
                          <style>
                            body {
                              margin: 0;
                              padding: 0;
                              background-color: #f5f6f8;
                              font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
                            }
                    
                            .email-container {
                              max-width: 480px;
                              margin: 50px auto;
                              padding: 20px;
                              background-color: #ffffff;
                              border-radius: 12px;
                              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                            }
                    
                            .header {
                              text-align: center;
                              margin-bottom: 30px;
                            }
                    
                            .header h1 {
                              font-size: 24px;
                              color: #333333;
                              margin: 0;
                            }
                    
                            .content {
                              text-align: center;
                              padding: 0 10px;
                            }
                    
                            .code-box {
                              display: inline-block;
                              padding: 16px 24px;
                              margin-top: 20px;
                              background-color: #e3f2ff;
                              border: 1px solid #90caf9;
                              border-radius: 8px;
                            }
                    
                            .code-box h3 {
                              margin: 0;
                              font-size: 16px;
                              color: #555555;
                            }
                    
                            .code-box h2 {
                              margin: 8px 0 0 0;
                              font-size: 32px;
                              color: #1976d2;
                              letter-spacing: 2px;
                              font-family: 'Courier New', Courier, monospace;
                            }
                    
                            .footer {
                              margin-top: 30px;
                              font-size: 14px;
                              color: #666666;
                              text-align: center;
                            }
                          </style>
                        </head>
                        <body>
                          <div class="email-container">
                            <div class="header">
                              <h1>호라이즌 인증 코드</h1>
                            </div>
                            <div class="content">
                              <p>아래 인증 코드를 입력하여 본인 확인을 완료해주세요.</p>
                              <div class="code-box">
                                <h3>인증 코드</h3>
                                <h2>%s</h2>
                              </div>
                              <p class="footer">이 코드는 잠시 후 만료됩니다.</p>
                            </div>
                          </div>
                        </body>
                        </html>
                    
                """.formatted(code);

            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new FailedSendMailException();
        }
    }

}
