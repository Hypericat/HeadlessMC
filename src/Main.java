public class Main {
    //make absolutely nothing static except for final variables in order to allow for multi clients

    public static void main(String[] args) throws InterruptedException {
        HeadlessInstance headless = new HeadlessInstance();
        headless.connect("192.168.2.226");
        headless.run();

    }
}