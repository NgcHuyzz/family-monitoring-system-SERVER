package com.family.server.api;

import com.family.server.controller.deviceController;
import com.family.server.controller.ScreenshotController;
import com.family.server.controller.KeyStrokeController;
import com.family.server.controller.PolicyController;
import com.family.server.model.Device;
import com.family.server.model.Screenshot;
import com.family.server.model.KeyStroke;
import com.family.server.model.Policy;
import com.family.server.security.JwtUtil;
import com.family.server.service.DecipherAES;
import com.family.server.service.ServerCommand;
import com.google.gson.Gson;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.Base64;

public class DeviceServlet extends HttpServlet {

    private final Gson gson = new Gson();

    // ------------------- ENTRY POINTS -------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo(); // null, "/", "/{id}", "/{id}/screenshots", ...

        if (pathInfo == null || "/".equals(pathInfo)) {
            // GET /api/devices
            handleListDevices(req, resp);
            return;
        }

        List<String> segs = splitPath(pathInfo);
        if (segs.size() == 1) {
            // GET /api/devices/{id}  (optional: hiện giờ có thể chưa dùng)
            handleDeviceDetail(req, resp, segs.get(0));
        } else if (segs.size() == 2) {
            String deviceId = segs.get(0);
            String action = segs.get(1);

            switch (action) {
                case "screenshots":
                    handleGetScreenshots(req, resp, deviceId);
                    break;
                case "keystrokes":
                    handleGetKeystrokes(req, resp, deviceId);
                    break;
                case "policy":
                    handleGetPolicy(req, resp, deviceId);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            // POST /api/devices
            handleCreateDevice(req, resp);
            return;
        }

        List<String> segs = splitPath(pathInfo);
        if (segs.size() == 2) {
            String deviceId = segs.get(0);
            String action = segs.get(1);

            switch (action) {
                case "policy":
                    handleSavePolicy(req, resp, deviceId);
                    break;
                case "command":
                    handleSendCommand(req, resp, deviceId);
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ------------------- HANDLERS -------------------

    // GET /api/devices
    private void handleListDevices(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = getUserIdFromToken(req);
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        deviceController dc = new deviceController();
        List<Device> devices = dc.getListDeviceOfUser(userId);

        writeJson(resp, devices);
    }

    // OPTIONAL: GET /api/devices/{id} (chi tiết 1 device, hiện có thể chưa cần dùng)
    private void handleDeviceDetail(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {
        // Nếu sau này cần, bạn có thể thêm DeviceDAO.getById() để trả thông tin chi tiết.
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Device detail API chưa implement");
        result.put("deviceId", deviceId);
        writeJson(resp, result);
    }

    // POST /api/devices
    private void handleCreateDevice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = getUserIdFromToken(req);
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Map<String, Object> body = parseJsonBody(req);
        String name = body.get("name") != null ? body.get("name").toString().trim() : "";

        Map<String, Object> result = new HashMap<>();

        if (name.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result.put("success", false);
            result.put("message", "Tên thiết bị không được trống");
            writeJson(resp, result);
            return;
        }

        deviceController dc = new deviceController();

        if (dc.checkName(name)) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            result.put("success", false);
            result.put("message", "Tên thiết bị đã tồn tại");
            writeJson(resp, result);
            return;
        }

        String id = dc.addDevice(name, userId);

        result.put("success", true);
        result.put("id", id);
        result.put("name", name);
        writeJson(resp, result);
    }

    // GET /api/devices/{deviceId}/screenshots
    private void handleGetScreenshots(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {

        ScreenshotController sc = new ScreenshotController();
        List<Screenshot> li = sc.getScreenshotDeviceID(deviceId);

        List<Map<String, Object>> dto = new ArrayList<>();
        int count = 0;

        for (Screenshot s : li) {
            Map<String, Object> item = new HashMap<>();
            item.put("timestamp", s.getTs()); // hoặc s.getTs().toString()

            // Encode ảnh sang base64 để FE hiển thị dạng data URL
            String base64 = Base64.getEncoder().encodeToString(s.getImgData());
            item.put("imageBase64", base64);

            dto.add(item);
            count++;
            if (count >= 10) break;  // giống ScreenshotPanel: chỉ lấy 10 ảnh
        }

        writeJson(resp, dto);
    }

    // GET /api/devices/{deviceId}/keystrokes
    private void handleGetKeystrokes(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {

        KeyStrokeController kc = new KeyStrokeController();
        List<KeyStroke> li = kc.getAllOfDevice(deviceId);

        List<Map<String, Object>> dto = new ArrayList<>();

        for (KeyStroke k : li) {
            Map<String, Object> item = new HashMap<>();
            String text = DecipherAES.Decipher(k.getTextEnc(), k.getIv());

            item.put("timestamp", k.getCreateAt()); // hoặc .toString()
            item.put("text", text);

            dto.add(item);
        }

        writeJson(resp, dto);
    }

    // GET /api/devices/{deviceId}/policy
    private void handleGetPolicy(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {

        PolicyController pc = new PolicyController();
        Policy model = pc.getPolicyByDeviceID(deviceId);

        if (model == null) {
            // Không có policy → trả rỗng
            Map<String, Object> result = new HashMap<>();
            result.put("exists", false);
            writeJson(resp, result);
        } else {
            // Gson sẽ convert Policy thành JSON
            writeJson(resp, model);
        }
    }

    // POST /api/devices/{deviceId}/policy
    private void handleSavePolicy(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {

        Map<String, Object> body = parseJsonBody(req);

        // vì JSON parse sang Map<String,Object> nên số thường thành Double
        int dailyTimeQuote = 0;
        if (body.get("dailyTimeQuote") != null) {
            dailyTimeQuote = ((Number) body.get("dailyTimeQuote")).intValue();
        }

        Map<String, Object> quietHour =
                body.get("quietHour") instanceof Map ? (Map<String, Object>) body.get("quietHour") : new HashMap<>();

        List<String> domainBlackList =
                body.get("domainBlackList") instanceof List ? (List<String>) body.get("domainBlackList") : new ArrayList<>();

        List<String> keywordBlackList =
                body.get("keywordBlackList") instanceof List ? (List<String>) body.get("keywordBlackList") : new ArrayList<>();

        List<String> appWhiteList =
                body.get("appWhiteList") instanceof List ? (List<String>) body.get("appWhiteList") : new ArrayList<>();

        PolicyController pc = new PolicyController();
        Policy model = pc.getPolicyByDeviceID(deviceId);

        if (model == null) {
            // Tạo mới
            pc.addPolicy(deviceId, dailyTimeQuote, quietHour, domainBlackList, keywordBlackList, appWhiteList);
        } else {
            // Cập nhật
            model.setDailyTimeQuote(dailyTimeQuote);
            model.setQuietHour(quietHour);
            model.setDomainBlackList(domainBlackList);
            model.setKeywordBlackList(keywordBlackList);
            model.setAppWhiteList(appWhiteList);

            pc.updatePolicy(model);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        writeJson(resp, result);
    }

    // POST /api/devices/{deviceId}/command
    private void handleSendCommand(HttpServletRequest req, HttpServletResponse resp, String deviceId)
            throws IOException {

        Map<String, Object> body = parseJsonBody(req);
        String action = body.get("action") != null ? body.get("action").toString() : null;

        Map<String, Object> result = new HashMap<>();

        if (action == null || action.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            result.put("success", false);
            result.put("message", "action is required");
            writeJson(resp, result);
            return;
        }

        // Hiện tại ServerCommand chưa phân biệt deviceId
        // sau này bạn có thể mở rộng để gửi đúng agent theo DeviceID
        ServerCommand serverCommand = new ServerCommand();
        serverCommand.sendCommand(action);

        result.put("success", true);
        result.put("deviceId", deviceId);
        result.put("action", action);
        writeJson(resp, result);
    }

    // ------------------- HELPER METHODS -------------------

    private List<String> splitPath(String pathInfo) {
        String[] arr = pathInfo.split("/");
        List<String> segs = new ArrayList<>();
        for (String s : arr) {
            if (!s.isEmpty()) segs.add(s);
        }
        return segs;
    }

    private Map<String, Object> parseJsonBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = req.getReader()) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        if (sb.length() == 0) return new HashMap<>();
        return gson.fromJson(sb.toString(), Map.class);
    }

    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.getWriter().write(gson.toJson(data));
    }

    private String getUserIdFromToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        try {
            return JwtUtil.getUserId(token);
        } catch (Exception e) {
            return null;
        }
    }
}
