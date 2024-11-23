import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieChartView extends JPanel {

    private Map<String, Double> data;
    private Color[] colors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.CYAN, Color.MAGENTA,
            new Color(128, 0, 128), // Custom Purple
            new Color(255, 165, 0)  // Custom Orange
    };

    public PieChartView(Map<String, Double> data) {
        this.data = data;
        setPreferredSize(new Dimension(500, 500)); // Adjust JPanel size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieChartWithLabels(g);
    }

    private void drawPieChartWithLabels(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        int startAngle = 0;

        int chartSize = 400; // Size of the chart
        int centerX = 250;   // Center X of the chart
        int centerY = 250;   // Center Y of the chart
        int i = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            // Calculate slice size
            double percentage = (entry.getValue() / total) * 360;

            // Draw the slice
            g2d.setColor(colors[i % colors.length]);
            g2d.fillArc(50, 50, chartSize, chartSize, startAngle, (int) Math.round(percentage));

            // Calculate the midpoint angle for the slice
            int midAngle = startAngle + (int) Math.round(percentage / 2);

            // Calculate the label position using trigonometry
            int labelRadius = chartSize / 2 + 20; // Distance from center for labels
            double radians = Math.toRadians(midAngle);
            int labelX = centerX + (int) (Math.cos(radians) * labelRadius);
            int labelY = centerY - (int) (Math.sin(radians) * labelRadius);

            // Draw the label
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), labelX - 15, labelY + 5); // Center label around point

            // Increment for the next slice
            startAngle += (int) Math.round(percentage);
            i++;
        }
    }

    public static void main(String[] args) {
        Map<String, Double> dummyData = Map.of(
                "Category A", 30.0,
                "Category B", 50.0,
                "Category C", 20.0
        );

        PieChartView chart = new PieChartView(dummyData);

        JFrame frame = new JFrame("Pie Chart with Labels");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chart);
        frame.pack(); // Adjust frame size automatically to fit JPanel
        frame.setVisible(true);
    }
}


