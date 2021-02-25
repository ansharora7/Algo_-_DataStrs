import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture pic;
    private int height, width, area;
    private int[] map;
    private int[] colors;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        pic = new Picture(picture);
        height = pic.height();
        width = pic.width();
        area = height * width;
        map = new int[area * 2];
        mapping(pic);

    }

    ////////////////////////////////////////////////////////////////////////
    private void mapping(Picture pic) {
        height = pic.height();
        width = pic.width();
        area = height * width;
        colors = new int[area];
        int count = 0, color_count = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                map[count] = i;
                map[++count] = j;
                colors[color_count++] = pic.getRGB(i, j);
                count += 1;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    // current picture
    public Picture picture() {
        return pic;
    }

    ////////////////////////////////////////////////////////////////////
    // width of current picture
    public int width() {
        return pic.width();
    }

    ////////////////////////////////////////////////////////////////////
    // height of current picture
    public int height() {
        return pic.height();
    }

    ///////////////////////////////////////////////////////////////////
    // create horizontal map
    private EdgeWeightedDigraph hor_map() {
        DirectedEdge edge;
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(area + 2);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double wt1 = energy(j, i);
                int coord_x1 = i * width + j;

                if (j == width - 1) {
                    edge = new DirectedEdge(coord_x1, area, 1000);
                    graph.addEdge(edge);
                }
                else {
                    if (j == 0) {
                        edge = new DirectedEdge(area + 1, coord_x1, 1000);
                        graph.addEdge(edge);
                    }
                    if (i > 0 && i < height - 1) {
                        double wt2 = energy(j + 1, i);
                        int coord_x2 = i * width + (j + 1);  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i + 1);
                        coord_x2 = (i + 1) * width + (j + 1); // down
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i - 1);
                        coord_x2 = (i - 1) * width + (j + 1); // up
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                    }
                    else if (height == 1) {
                        double wt2 = energy(j + 1, i);
                        int coord_x2 = i * width + (j + 1);  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                    else if (i == 0) {
                        double wt2 = energy(j + 1, i);
                        int coord_x2 = i * width + (j + 1);  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i + 1);
                        coord_x2 = (i + 1) * width + (j + 1); // down
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                    else if (i == height - 1) {
                        double wt2 = energy(j + 1, i);
                        int coord_x2 = i * width + (j + 1);  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i - 1);
                        coord_x2 = (i - 1) * width + (j + 1); // up
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                }
            }
        }
        return graph;
    }

    //////////////////////////////////////////////////////////////////
    // create vertical map
    private EdgeWeightedDigraph vert_map() {
        DirectedEdge edge;
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(area + 2);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double wt1 = energy(j, i);
                int coord_x1 = i * width + j;

                if (i == height - 1) {
                    edge = new DirectedEdge(coord_x1, area, 1000);
                    graph.addEdge(edge);
                }
                else {
                    if (i == 0) {
                        edge = new DirectedEdge(area + 1, coord_x1, 1000);
                        graph.addEdge(edge);
                    }
                    if (j > 0 && j < width - 1) {
                        double wt2 = energy(j, i + 1);
                        int coord_x2 = (i + 1) * width + j;  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i + 1);
                        coord_x2 = (i + 1) * width + (j + 1); // right
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j - 1, i + 1);
                        coord_x2 = (i + 1) * width + (j - 1); // left
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                    }
                    else if (width == 1) {
                        double wt2 = energy(j, i + 1);
                        int coord_x2 = (i + 1) * width + j;  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                    else if (j == 0) {
                        double wt2 = energy(j, i + 1);
                        int coord_x2 = (i + 1) * width + j;  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j + 1, i + 1);
                        coord_x2 = (i + 1) * width + (j + 1); // right
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                    else if (j == width - 1) {
                        double wt2 = energy(j, i + 1);
                        int coord_x2 = (i + 1) * width + j;  // front
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);

                        wt2 = energy(j - 1, i + 1);
                        coord_x2 = (i + 1) * width + (j - 1); // left
                        edge = new DirectedEdge(coord_x1, coord_x2, wt1 + wt2);
                        graph.addEdge(edge);
                    }
                }
            }
        }
        return graph;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!(x >= 0 && x <= width - 1) || !(y >= 0 && y <= height - 1)) {
            throw new IllegalArgumentException();
        }
        double energy_x;
        double energy_y;
        double final_energy;
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) final_energy = 1000;
        else {
            // For coord x
            int rgb_left = pic.getRGB(x - 1, y);
            int rgb_right = pic.getRGB(x + 1, y);
            Color cl_left = new Color(rgb_left);
            Color cl_right = new Color(rgb_right);
            double diff_red = Math.pow(cl_right.getRed() - cl_left.getRed(), 2);
            double diff_green = Math.pow(cl_right.getGreen() - cl_left.getGreen(), 2);
            double diff_blue = Math.pow(cl_right.getBlue() - cl_left.getBlue(), 2);
            energy_x = diff_red + diff_green + diff_blue;

            // For coord y
            rgb_left = pic.getRGB(x, y - 1);
            rgb_right = pic.getRGB(x, y + 1);
            cl_left = new Color(rgb_left);
            cl_right = new Color(rgb_right);
            diff_red = Math.pow(cl_right.getRed() - cl_left.getRed(), 2);
            diff_green = Math.pow(cl_right.getGreen() - cl_left.getGreen(), 2);
            diff_blue = Math.pow(cl_right.getBlue() - cl_left.getBlue(), 2);
            energy_y = diff_red + diff_green + diff_blue;

            final_energy = Math.sqrt(energy_x + energy_y);
        }
        return final_energy;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (width == 1) {
            return new int[] { 0 };
        }
        Iterable<DirectedEdge> path;
        EdgeWeightedDigraph hor_seam = new EdgeWeightedDigraph(hor_map());
        DijkstraSP sp = new DijkstraSP(hor_seam, area + 1);
        path = sp.pathTo(area);
        int[] coords = new int[width];
        int i = 0;
        for (DirectedEdge k : path) {
            if (!(k.from() >= area)) {
                coords[i] = map[k.from() * 2 + 1];
                i += 1;
            }
        }
        return coords;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (height == 1) {
            return new int[] { 0 };
        }
        Iterable<DirectedEdge> path;
        EdgeWeightedDigraph vert_seam = new EdgeWeightedDigraph(vert_map());
        DijkstraSP sp = new DijkstraSP(vert_seam, area + 1);
        path = sp.pathTo(area);
        int[] coords = new int[height];
        int i = 0;
        for (DirectedEdge k : path) {
            if (!(k.from() >= area)) {
                coords[i] = map[k.from() * 2];
                i += 1;
            }
        }

        return coords;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != width) throw new IllegalArgumentException();

        for (int k = 0; k < seam.length; k++) {
            if (seam[k] < 0 || seam[k] > height - 1) throw new IllegalArgumentException();
            if (k < seam.length - 1) {
                if (seam[k] - seam[k + 1] < -1 || seam[k] - seam[k + 1] > 1)
                    throw new IllegalArgumentException();
            }
        }

        Picture new_pic = new Picture(width, height - 1);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (j < seam[i]) {
                    new_pic.setRGB(i, j,
                                   colors[j * width
                                           + i]);  // coord= curr_height*width+curr_width
                }
                else if (j > seam[i]) {
                    new_pic.setRGB(i, j - 1, colors[j * width + i]);
                }
            }
        }
        pic = new Picture(new_pic);
        mapping(pic);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != height) throw new IllegalArgumentException();

        for (int k = 0; k < seam.length; k++) {
            if (seam[k] < 0 || seam[k] > width - 1) throw new IllegalArgumentException();
            if (k < seam.length - 1) {
                if (seam[k] - seam[k + 1] < -1 || seam[k] - seam[k + 1] > 1)
                    throw new IllegalArgumentException();
            }
        }

        Picture new_pic = new Picture(width - 1, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j < seam[i]) {
                    new_pic.setRGB(j, i, colors[i * width + j]);
                }
                else if (j > seam[i]) {
                    new_pic.setRGB(j - 1, i, colors[i * width + j]);
                }
            }
        }
        pic = new Picture(new_pic);
        mapping(pic);
    }


    //  unit testing (optional)
    public static void main(String[] args) {
        Picture pic = new Picture(
                "/home/acer/Desktop/Ansh/Algoritms Part-2/Assignment Week 2/6x5.png");
        Picture pic2 = new Picture(pic);
        SeamCarver sc = new SeamCarver(pic);
        int[] arr = sc.findHorizontalSeam();
        for (int i = 0; i < arr.length; i++) System.out.println(arr[i]);
        System.out.println("//////////////////////////////////////////");
        sc.removeHorizontalSeam(arr);
        int[] arr2 = sc.findVerticalSeam();
        for (int i = 0; i < arr2.length; i++) System.out.println(arr2[i]);
        sc.removeVerticalSeam(arr2);
        System.out.println(pic2.equals(sc.pic));
        // sc.removeVerticalSeam(new int[] { 4, 5, 7, 8, 8, 7, 7, 6, 5, 5 });
    }

}
