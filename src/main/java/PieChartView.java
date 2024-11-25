import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PieChartView extends JPanel {

    private Map<String, Double> data;
    private Color[] pastelColors = {
            new Color(174, 41, 51),
            new Color(121, 236, 121), // Light Green
            new Color(45, 136, 154), // Light Blue
            new Color(127, 41, 179, 255), // Light Pink
            new Color(214, 197, 15), // Light Yellow
            new Color(126, 88, 151), // Navajo White
            new Color(255, 182, 193), // Light Pink
            new Color(240, 230, 140), // Khaki
            new Color(175, 238, 238), // Pale Turquoise
            new Color(255, 239, 213)  // Papaya Whip
    };

    public PieChartView(Map<String, Double> data) {
        this.data = data;
        setPreferredSize(new Dimension(1000, 800)); // Adjust JPanel size
        setBackground(Color.DARK_GRAY); // Set panel background color
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

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int chartSize = Math.min(panelWidth, panelHeight) - 100; // Size of the chart
        int centerX = panelWidth / 2 - chartSize / 2; // Center X
        int centerY = panelHeight / 2 - chartSize / 2; // Center Y
        int centerXChart = panelWidth / 2; // Exact X center
        int centerYChart = panelHeight / 2; // Exact Y center

        // Set font to Broadway with a fallback to Arial if unavailable
        Font broadwayFont = new Font("Broadway", Font.BOLD, 20);
        g2d.setFont(broadwayFont);
        FontMetrics metrics = g2d.getFontMetrics();


        int i = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            // Calculate slice size
            double percentage = (entry.getValue() / total) * 360;

            // Draw the slice
            g2d.setColor(pastelColors[i % pastelColors.length]);
            g2d.fillArc(centerX, centerY, chartSize, chartSize, startAngle, (int) Math.round(percentage));

            // Calculate the midpoint angle for the slice
            int midAngle = startAngle + (int) Math.round(percentage / 2);

            // Calculate the label position using trigonometry (slightly inside the slice)
            int labelRadius = chartSize / 3; // Slightly inside the chart
            double radians = Math.toRadians(midAngle);
            int labelX = centerXChart + (int) (Math.cos(radians) * labelRadius);
            int labelY = centerYChart - (int) (Math.sin(radians) * labelRadius);

            // Draw the label centered within the slice
            String label = entry.getKey();
            int textWidth = metrics.stringWidth(label);
            int textHeight = metrics.getHeight();
            g2d.setColor(Color.BLACK); // Text color
            g2d.drawString(label, labelX - textWidth / 2, labelY + textHeight / 4);

            // Increment for the next slice
            startAngle += (int) Math.round(percentage);
            i++;
        }
    }

    public static void main(String[] args) {
        UserInfoClass userInfoClass = new UserInfoClass();
        Map<String, Double> genreData = userInfoClass.organizeTracks();

        PieChartView chartView = new PieChartView(genreData);


        JFrame frame = new JFrame("Your Playlist: Decomposed by Spotilyze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartView);
        frame.pack(); // Adjust frame size automatically to fit JPanel
        frame.setVisible(true);
    }
}

