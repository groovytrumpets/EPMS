package controller.common;

import DAO.LeaveDAO;
import model.LeaveRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "LeaveServlet", urlPatterns = {"/leave"})
public class LeaveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        LeaveDAO dao = new LeaveDAO();
        if ("balance".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int balance = dao.getLeaveBalance(userId);
            request.setAttribute("balance", balance);
            request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
        } else if ("list".equals(action)) {
            List<LeaveRequest> list = dao.getAllLeaveRequests();
            request.setAttribute("leaveList", list);
            request.getRequestDispatcher("leaveListHR.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        LeaveDAO dao = new LeaveDAO();
        if ("submit".equals(action)) {
            // Employee gửi đơn
            int userId = Integer.parseInt(request.getParameter("userId"));
            LocalDate start = LocalDate.parse(request.getParameter("startDate"));
            LocalDate end = LocalDate.parse(request.getParameter("endDate"));
            String reason = request.getParameter("reason");

            // Validate ngày bắt đầu không ở quá khứ
            if (start.isBefore(LocalDate.now())) {
                request.setAttribute("error", "Ngày bắt đầu không được ở quá khứ!");
                request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
                return;
            }
            // Validate ngày kết thúc >= ngày bắt đầu
            if (end.isBefore(start)) {
                request.setAttribute("error", "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu!");
                request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
                return;
            }
            // Validate lý do không để trống, không ký tự đặc biệt nguy hiểm
            if (reason == null || reason.trim().isEmpty() || !reason.matches("^[\\p{L}0-9 .,!?:;()\\n\\-]+$") ) {
                request.setAttribute("error", "Lý do không hợp lệ!");
                request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
                return;
            }
            // Validate số ngày xin nghỉ không vượt quá số ngày phép còn lại
            int days = (int) (end.toEpochDay() - start.toEpochDay() + 1);
            int balance = dao.getLeaveBalance(userId);
            if (days > balance) {
                request.setAttribute("error", "Số ngày xin nghỉ vượt quá số ngày phép còn lại!");
                request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
                return;
            }
            LeaveRequest req = new LeaveRequest();
            req.setUserId(userId);
            req.setStartDate(start);
            req.setEndDate(end);
            req.setReason(reason);
            boolean success = dao.createLeaveRequest(req);
            if (success) {
                request.setAttribute("message", "Gửi đơn xin nghỉ thành công!");
            } else {
                request.setAttribute("error", "Gửi đơn thất bại!");
            }
            request.getRequestDispatcher("leaveForm.jsp").forward(request, response);
        } else if ("approve".equals(action) || "reject".equals(action)) {
            // HR duyệt hoặc từ chối
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            int hrId = Integer.parseInt(request.getParameter("hrId"));
            String note = request.getParameter("note");
            // Validate ghi chú không để trống
            if (note == null || note.trim().isEmpty()) {
                request.setAttribute("error", "Ghi chú không được để trống!");
                List<LeaveRequest> list = dao.getAllLeaveRequests();
                request.setAttribute("leaveList", list);
                request.getRequestDispatcher("leaveListHR.jsp").forward(request, response);
                return;
            }
            boolean success = false;
            if ("approve".equals(action)) {
                success = dao.approveLeaveRequest(requestId, hrId, note);
            } else {
                success = dao.rejectLeaveRequest(requestId, hrId, note);
            }
            if (success) {
                request.setAttribute("message", "Cập nhật trạng thái đơn thành công!");
            } else {
                request.setAttribute("error", "Cập nhật trạng thái đơn thất bại!");
            }
            List<LeaveRequest> list = dao.getAllLeaveRequests();
            request.setAttribute("leaveList", list);
            request.getRequestDispatcher("leaveListHR.jsp").forward(request, response);
        }
    }
} 