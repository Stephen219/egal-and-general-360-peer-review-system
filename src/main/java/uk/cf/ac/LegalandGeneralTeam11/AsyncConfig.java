package uk.cf.ac.LegalandGeneralTeam11;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * This class is used to enable asynchronous processing in the application.
 * i added this to enhance the performance of the application in terms of sending emails.
 * This is done by sending emails in the background without blocking the main thread.
 * This is done by annotating the class with @EnableAsync.
 */

@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig {



}
