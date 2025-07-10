package controller.HR;

import DAO.ReminderDAO;
import java.util.concurrent.*;import java.time.LocalDateTime;

public class ScheduleReminderService {
    private ScheduledExecutorService scheduler;

    public void start() {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            System.out.println("⏰ [Scheduler] Running check at " + LocalDateTime.now());
            try {
                checkAndSendMail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Delay ban đầu 0 giây, chạy lại sau mỗi 24 giờ
        scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }

    private void checkAndSendMail() {
        // Gọi DAO để kiểm tra DB và gửi mail
        ReminderDAO dao = new ReminderDAO();
        dao.checkAndSendReminderEmails();
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
