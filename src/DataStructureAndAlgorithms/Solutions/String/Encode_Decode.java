package DataStructureAndAlgorithms.Solutions.String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataStructureAndAlgorithms.Base_Solution;

public class Encode_Decode extends Base_Solution<Boolean> {

    public final List<String> list = new ArrayList<>(Arrays.asList("neet", "code", "love", "you"));

    @Override
    protected Boolean solve() {
        String encodedString = encode(list);
        List<String> decodedList = decode(encodedString);
        boolean answer = true;
        for (int i = 0; i < list.size(); i++) {
            if (!decodedList.get(i).equals(list.get(i))) {
                answer = false;
            }
        }
        System.out.println(decodedList);
        System.out.println(list);
        return answer;
    }

    private String encode(List<String> strings) {
        StringBuilder encoded = new StringBuilder();
        for (String s : strings) {
            encoded.append(s.length()).append('#').append(s);
        }
        return encoded.toString();
    }

    private List<String> decode(String encodedString) {
        List<String> decoded = new ArrayList<>();
        int i = 0;

        while (i < encodedString.length()) {
            int delimiterIndex = encodedString.indexOf('#', i);
            int length = Integer.parseInt(encodedString.substring(i, delimiterIndex));
            i = delimiterIndex + 1;
            String s = encodedString.substring(i, i + length);
            decoded.add(s);
            i += length;
        }

        return decoded;
    }

    public static void main(String[] args) {
        Encode_Decode solution = new Encode_Decode();
        solution.solve();
    }
}
