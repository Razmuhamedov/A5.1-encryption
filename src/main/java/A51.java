import java.util.Arrays;
import java.util.Random;

public class A51 {

    private final int sizeX = 19;
    private final int sizeY = 22;
    private final int sizeZ = 23;

    private final int[] regX = new int[sizeX];
    private final int[] regY = new int[sizeY];
    private final int[] regZ = new int[sizeZ];
    private int[] key;

    private int[] randVectorInit(){
        System.out.print("Random vector initialization: ");
        int[] vectorInit = new int[64];
        Random random = new Random();
        for (int i = 0; i< vectorInit.length; i++){
            vectorInit[i] = (byte) random.nextInt(2);
            System.out.print(vectorInit[i]);
        }
        System.out.println();
        return vectorInit;
    }

    private void setStartRegisters(int[] vectorInit){
        for (int i = 0; i < vectorInit.length; i++) {
            regX[0] = (regX[13] ^ regX[16] ^ regX[17] ^ regX[18]) ^ vectorInit[i];
            for (int j = sizeX - 1; j > 0; j--) {
                regX[j] = regX[j - 1];
            }
            regY[0] = (regY[20] ^ regY[21]) ^ vectorInit[i];
            for (int j = sizeY - 1; j > 0; j--) {
                regY[j] = regY[j - 1];
            }
            regZ[0] = (regZ[7] ^ regZ[20] ^ regZ[21] ^ regZ[22]) ^ vectorInit[i];
            for (int j = sizeZ - 1; j > 0; j--) {
                regZ[j] = regZ[j - 1];
            }
            System.out.println("\nCycle - " + i);
            System.out.println("Initial register X: " + Arrays.toString(regX));
            System.out.println("Initial register Y: " + Arrays.toString(regY));
            System.out.println("Initial register Z: " + Arrays.toString(regZ));
        }
    }

    public int[] getKey(int length){
        int[] keyStream = new int[length];
        setStartRegisters(randVectorInit());
        System.out.print("Key ");
        int endX = 0, endY = 0, endZ = 0;
        for (int i = 0; i < length; i++){
            int maj = getMajor(regX[8], regY[10], regZ[10]);
            if(maj == regX[8]){
                endX = regX[18];
                int temp = regX[13] ^ regX[16] ^ regX[17] ^ regX[18];
                for (int j = sizeX - 1; j > 0; j--) {
                    regX[j] = regX[j - 1];
                }
                regX[0] = temp;
            }
            if(maj == regY[10]){
                endY = regY[21];
                int temp = regY[20] ^ regY[21];
                for (int j = sizeY - 1; j > 0; j--) {
                    regY[j] = regY[j - 1];
                }
                regY[0] = temp;
            }
            if(maj == regZ[10]){
                endZ = regZ[22];
                int temp = regZ[7] ^ regZ[20] ^ regZ[21] ^ regZ[22];
                for (int j = sizeZ - 1; j > 0; j--) {
                    regZ[j] = regZ[j - 1];
                }
                regZ[0] = temp;
            }
            System.out.println("\nCycle with major - " + i);
            System.out.println("Initial register X: " + Arrays.toString(regX));
            System.out.println("Initial register Y: " + Arrays.toString(regY));
            System.out.println("Initial register Z: " + Arrays.toString(regZ));
            keyStream[i] = endX ^ endY ^ endZ;
        }
        System.out.print(Arrays.toString(keyStream));
        System.out.println("\nKey length: " + keyStream.length);
        return keyStream;
    }

    public String encrypt(String openText){
        int[] message = toBinary(openText);
        key = getKey(message.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length; i++) {
            sb.append(message[i] ^ key[i]);
        }
        System.out.println("Cyber binary: " + sb);
        return toString(sb.toString());
    }

    public String decrypt(String CyperText){
        int[] message = toBinary(CyperText);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length; i++) {
            sb.append(message[i] ^ key[i]);
        }
        return toString(sb.toString());
    }

    private String toString(String text){
        StringBuilder str;
        str = new StringBuilder();
        for (int i = 0; i < text.length()/8; i++) {

            int a = Integer.parseInt(text.substring(8*i,(i+1)*8),2);
            str.append((char) (a));
        }
        return str.toString();
    }

    private int[] toBinary(String text){
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        System.out.println("Binary format " + result.length() + ": " + result);
        int[] binary = new int[result.length()];
        for (int i = 0; i < binary.length; i++){
            binary[i] = Integer.parseInt(result.substring(i, i + 1));
        }
        return binary;
    }

    private int getMajor(int x8, int y10, int z10){
        return (x8 ^ y10 ^ z10) == 1 ? 1 : 0;
    }
}
