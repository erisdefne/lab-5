import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PieChartView extends JPanel {

    private Map<String, Double> data;
    private Color[] pastelColors = {
            new Color(255, 97, 110),
            new Color(165, 243, 165), // Light Green
            new Color(88, 192, 221), // Light Blue
            new Color(194, 100, 244), // Light Pink
            new Color(237, 224, 123), // Light Yellow
            new Color(211, 150, 255), // Navajo White
            new Color(255, 182, 193), // Light Pink
            new Color(240, 230, 140), // Khaki
            new Color(175, 238, 238), // Pale Turquoise
            new Color(255, 239, 213),  // Papaya Whip
            new Color(224, 187, 228), // Pastel Lavender
            new Color(198, 227, 245), // Powder Blue
            new Color(255, 223, 186), // Apricot
            new Color(203, 232, 202), // Mint Green
            new Color(255, 201, 201), // Pastel Pink
            new Color(250, 235, 215), // Antique White
            new Color(240, 222, 204), // Almond
            new Color(229, 204, 255), // Mauve
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

        Font broadwayFont = new Font("Arial", Font.BOLD, 10);
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
            AffineTransform originalTransform = g2d.getTransform(); // Translate to label position
            g2d.translate(labelX, labelY); // Rotate to match slice direction
            double rotationAngle = -radians; // Default rotation
            if (midAngle >= 90 && midAngle <= 270) {
                rotationAngle += Math.PI; // Add 180° (π radians) for labels on the left half
            }
            g2d.rotate(rotationAngle); // Adjust rotation direction if needed

            // Draw the label centered within the slice
            String label = entry.getKey();
            int textWidth = metrics.stringWidth(label);
            int textHeight = metrics.getHeight();
            g2d.setColor(Color.BLACK); // Text color
            //g2d.drawString(label, labelX - textWidth / 2, labelY + textHeight / 4);

            g2d.drawString(label, 0, 0);
            // Reset transformation to avoid affecting subsequent drawings
            g2d.setTransform(originalTransform);

            // Increment for the next slice
            startAngle += (int) Math.round(percentage);
            i++;
        }
    }

    public static void main(String[] args) throws IOException {
        UserInfoClass userInfoClass = new UserInfoClass();
        Map<String, Integer> genreData = userInfoClass.getTopTrackGenres("medium_term", "50");

        // Convert Map<String, Integer> to Map<String, Double>, filtering out entries with value 0
        Map<String, Double> genreDataModified = genreData.entrySet().stream()
                .filter(entry -> entry.getValue() > 0) // Exclude entries where the value is 0
                .collect(Collectors.toMap(
                        Map.Entry::getKey,            // Keep the keys as they are
                        entry -> entry.getValue().doubleValue() // Convert Integer to Double
                ));


        PieChartView chartView = new PieChartView(genreDataModified);


        JFrame frame = new JFrame("Your Playlist: Decomposed by Spotilyze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartView);
        frame.pack(); // Adjust frame size automatically to fit JPanel
        frame.setVisible(true);
    }
}

