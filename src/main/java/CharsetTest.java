import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Pattern;

public class CharsetTest {
	public static void main(String[] args) {
		
		Charset cset = Charset.forName("819");
//		Set<String> aliases = cset.aliases();
//		for (String aliase : aliases) {
//			System.out.println(aliase);
//		}
//		
//		Map<String, Charset> availableCharsets = Charset.availableCharsets();
//		
//		for (String string : availableCharsets.keySet()) {
//			 System.out.println(string);
		
//		}
		String str = "xixixi";
		ByteBuffer encode = cset.encode(str);
		byte[]  bs =  encode.array();
		//System.out.println(bs);
		
		ByteBuffer wrap = ByteBuffer.wrap(bs, 0, bs.length);
		CharBuffer decode = cset.decode(wrap);
		String string = decode.toString();
		System.out.println(string);
	
		
	}

}
