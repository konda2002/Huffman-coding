package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the entire
 * Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte and NOT
     * as characters of 1 and 0 which take up 8 bits each
     * 
     * @param filename  The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding - 1; i++)
            pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                System.exit(1);
            }

            if (c == '1')
                currentByte += 1 << (7 - byteIndex);
            byteIndex++;

            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }

        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";

        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();

            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1')
                    return bitString.substring(i + 1);
            }

            return bitString.substring(8);
        } catch (Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /**
     * Reads a given text file character by character, and returns an arraylist of
     * CharFreq objects with frequency > 0, sorted by frequency
     * 
     * @param filename The text file to read from
     * @return Arraylist of CharFreq objects, sorted by frequency
     */
    public static ArrayList<CharFreq> makeSortedList(String filename) {
        StdIn.setFile(filename);

        int[] input1 = new int[128];
        double totchar = 0;
        while (StdIn.hasNextChar()) {

            char c = StdIn.readChar();
            totchar++;
            input1[(int) c]++;

        }

        ArrayList<CharFreq> result = new ArrayList<CharFreq>();

        for (int i = 0; i < input1.length; i++) {
            if (input1[i] > 0) {
                double reps = (double) input1[i];

                double prob = reps / totchar;
                CharFreq temp = new CharFreq((char) i, prob);
                result.add(temp);
            }
        }

        Collections.sort(result);



        return result;




    }

    /**
     * Uses a given sorted arraylist of CharFreq objects to build a huffman coding
     * tree
     * 
     * @param sortedList The arraylist of CharFreq objects to build the tree from
     * @return A TreeNode representing the root of the huffman coding tree
     */
    public static TreeNode makeTree(ArrayList<CharFreq> sortedList) {
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();

        for (int i = 0; i < sortedList.size(); i++) {
            TreeNode x = new TreeNode();
            x.setData(sortedList.get(i));
            source.enqueue(x);
        }

       




TreeNode one=new TreeNode();
TreeNode two=new TreeNode();

CharFreq zero=new CharFreq();
zero.setProbOccurrence(0);
TreeNode zeros=new TreeNode();
zeros.setData(zero);



if(source.size()==1){
    
    
    TreeNode l=new TreeNode();
CharFreq temp5=new CharFreq();
temp5.setCharacter(null);
temp5.setProbOccurrence(source.peek().getData().getProbOccurrence());
l.setData(temp5);
l.setLeft(source.dequeue());
l.setRight(null);
target.enqueue(l);


return target.dequeue();



} else{

while(!(source.isEmpty() && target.size()==1) ) {

    for(int i=1;i<=2;i++){


if((source.isEmpty() && target.isEmpty())){
    if(i==2){
    TreeNode main=new TreeNode();
CharFreq x= new CharFreq();
x.setCharacter(null);
x.setProbOccurrence(one.getData().getProbOccurrence());
main.setData(x);
main.setLeft(one);
target.enqueue(main);
return target.dequeue();
}
}else


        if(source.isEmpty()){
          
            if(i==1){
                one=target.dequeue();

            }else{
                two=target.dequeue();
            }

        }else if(target.isEmpty()){

            if(i==1){
                one=source.dequeue();
                
            }else{
                two=source.dequeue();
            }

        } 
        else if(source.peek().getData().getProbOccurrence()>target.peek().getData().getProbOccurrence()){
            if( i==1){
one=target.dequeue();
            }else{
two=target.dequeue();
            }
        } 
        else{

            if(i==1){
                one=source.dequeue();
                
            }else{
                two=source.dequeue();
            }
        }

        


    }

    TreeNode main=new TreeNode();
CharFreq x= new CharFreq();
x.setCharacter(null);
x.setProbOccurrence(one.getData().getProbOccurrence()+two.getData().getProbOccurrence());
main.setData(x);
main.setLeft(one);
main.setRight(two);
target.enqueue(main);


}

return target.dequeue();
}



    }

    /**
     * Uses a given huffman coding tree to create a string array of size 128, where
     * each index in the array contains that ASCII character's bitstring encoding.
     * Characters not present in the huffman coding tree should have their spots in
     * the array left null
     * 
     * @param root The root of the given huffman coding tree
     * @return Array of strings containing only 1's and 0's representing character
     *         encodings
     */
    public static String[] makeEncodings(TreeNode root) {
        /* Your code goes here */
        // ArrayList<Integer> x= new ArrayList<Integer>();
        // String[] y=new String[128];

        String[] z = new String[128];
        int index = 0;
        int arr[] = new int[400];

        printRootToLeafImpl(root, arr, index, z);

        return z;

    }

    private static void printRootToLeafImpl(TreeNode node, int[] arr, int index, String[] z) {
        if (node == null) {
            return;
        }

        if (node.getLeft() == null && node.getRight() == null) {

            int f = (int) node.getData().getCharacter();
            String g = "";

            for (int i = 0; i <= index - 1; i++) {
                g += arr[i];
            }

            z[f] = g;

        }

        if (node.getLeft() != null) {
            arr[index] = 0;
        }
        printRootToLeafImpl(node.getLeft(), arr, index + 1, z);
        if (node.getRight() != null) {
            arr[index] = 1;
        }
        printRootToLeafImpl(node.getRight(), arr, index + 1, z);
    }

    /**
     * Using a given string array of encodings, a given text file, and a file name
     * to encode into, this method makes use of the writeBitString method to write
     * the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodings   The array containing binary string encodings for each
     *                    ASCII character
     * @param textFile    The text file which is to be encoded
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public static void encodeFromArray(String[] encodings, String textFile, String encodedFile) {
        StdIn.setFile(textFile);
        /* Your code goes here */
        String encodedstring = "";
       
       
       
      while (StdIn.hasNextChar()) {
           Character letter=StdIn.readChar();
            int temp = (int) letter;

            encodedstring += encodings[temp] + "";

        } 

        writeBitString(encodedFile, encodedstring);
    }

    /**
     * Using a given encoded file name and a huffman coding tree, this method makes
     * use of the readBitString method to convert the file into a bit string, then
     * decodes the bit string using the tree, and writes it to a file.
     * 
     * @param encodedFile The file which contains the encoded text we want to decode
     * @param root        The root of your Huffman Coding tree
     * @param decodedFile The file which you want to decode into
     */
    public static void decode(String encodedFile, TreeNode root, String decodedFile) {
        StdOut.setFile(decodedFile);
        /* Your code goes here */

        String input = readBitString(encodedFile);
        String output = "";

        TreeNode ptr = root;

        for (int i = 0; i < input.length(); i++) {

            if (ptr.getLeft() == null && ptr.getRight() == null) {
                output += ptr.getData().getCharacter();
                ptr = root;
                
            }
            Character temp=input.charAt(i);
            if (temp == '1') {
                ptr = ptr.getRight();
            } else {
                ptr = ptr.getLeft();
            }



        }

        if (ptr.getLeft() == null && ptr.getRight() == null) {
            output += ptr.getData().getCharacter();
            
        }


       

        StdOut.print(output);

        

    }
}
