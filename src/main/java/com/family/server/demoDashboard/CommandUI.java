//package com.family.server.demoDashboard;
//
//import com.family.server.service.AgentChannel;
//import com.family.server.service.ServerCommand;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class CommandUI extends JFrame {
//
//    private final JComboBox<String> cbAction;
//    private final JTextArea txtLog;
//
//    private final AgentChannel agentChannel;
//
//    public CommandUI() {
//        super("Command UI (Demo)");
//        this.agentChannel = new AgentChannel();
//
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(500, 300);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel(new BorderLayout());
//
//        // ===== Panel input (action + button) =====
//        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // Combobox Action
//        inputPanel.add(new JLabel("Action:"));
//
//        cbAction = new JComboBox<>(new String[]{
//                "KILL_APP",
//                "SHUTDOWN",
//                "RESTART",
//                "LOCKSCREEN"
//        });
//        inputPanel.add(cbAction);
//
//        // Nút gửi lệnh
//        JButton btnSend = new JButton("Send Command");
//        inputPanel.add(new JLabel()); // ô trống cho đẹp grid
//        inputPanel.add(btnSend);
//
//        panel.add(inputPanel, BorderLayout.NORTH);
//
//        // ===== Log area =====
//        txtLog = new JTextArea();
//        txtLog.setEditable(false);
//        panel.add(new JScrollPane(txtLog), BorderLayout.CENTER);
//
//        // ===== Sự kiện nút Send =====
//        btnSend.addActionListener(e -> {
//            String action = (String) cbAction.getSelectedItem();
//
//            if (action == null || action.trim().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please select an action");
//                return;
//            }
//
//            agentChannel.sendCommand(,action);
//            txtLog.append("Sent: action=" + action + "\n");
//        });
//
//        setContentPane(panel);
//    }
//}
