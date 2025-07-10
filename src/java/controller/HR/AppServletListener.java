package controller.HR;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppServletListener implements ServletContextListener {

    private ScheduleReminderService reminderService;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App started: scheduler should run here.");
        // Ví dụ gọi scheduler
        reminderService = new ScheduleReminderService();
        reminderService.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (reminderService != null) {
            reminderService.stop();
        }
        System.out.println("App stopped.");
    }
}
