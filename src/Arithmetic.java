import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Arithmetic {

	/**
	 * ��ǰ�� ���ŵ����ȼ������
	 */
	private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();
	static {
		basic.put('-', 1);
		basic.put('+', 1);
		basic.put('*', 2);
		basic.put('/', 2);
		basic.put('(', 0);
	}

	/**
	 * �õ�����ʽ���ַ���
	 */
	public String getString() {
		String[] operate = new String[] { "+", "-", "*", "/" };
		int[] number = new int[101];
		for (int i = 0; i <= 100; i++)
			number[i] = i;
		int[] type = new int[3];
		for (int i = 0; i < 3; i++)
			type[i] = i;
		Random r = new Random();
		int t = type[r.nextInt(3)];
		if (t == 0){
			String  str1 = operate[r.nextInt(4)];
			String  str2 = operate[r.nextInt(4)];
			if (str1.equals(str2))
				return null;
			else 
				return number[r.nextInt(101)] + str1 + number[r.nextInt(101)] + str2
						+ number[r.nextInt(101)];			
		}
		else if (t == 1){
			String  str1 = operate[r.nextInt(4)];
			String  str2 = operate[r.nextInt(4)];
			String  str3 = operate[r.nextInt(4)];
			if(str1.equals(str2)&&str1.equals(str3))
				return null;
			else 
				return number[r.nextInt(101)] + str1 + number[r.nextInt(101)] + str2
						+ number[r.nextInt(101)] + str3 + number[r.nextInt(101)];
		}
		else{
			String  str1 = operate[r.nextInt(4)];
			String  str2 = operate[r.nextInt(4)];
			String  str3 = operate[r.nextInt(4)];
			String  str4 = operate[r.nextInt(4)];
			if(str1.equals(str2)&&str1.equals(str3)&&str1.equals(str4))
				return null;
			else
				return number[r.nextInt(101)] + str1 + number[r.nextInt(101)] + str2
						+ number[r.nextInt(101)] + str3 + number[r.nextInt(101)] + str4
						+ number[r.nextInt(101)];
		}
			
	}

	/**
	 * �� ��׺���ʽ ת��Ϊ ��׺���ʽ
	 */
	public String toSuffix(String infix) {
		if(infix==null)
			return null;
		List<String> queue = new ArrayList<String>();// ������� ���ڴ洢 ���� �Լ����� ��׺���ʽ
		List<Character> stack = new ArrayList<Character>();// ����ջ ���ڴ洢 �����,���stack�лᱻ ����

		char[] charArr = infix.trim().toCharArray();// �ַ����� ���ڲ�����ֻ����
		String standard = "*/+-()"; // �ж���׼ �����ʽ�л���ֵ������д����
		char ch = '&';// ��ѭ������������ �ַ�����ĵ�ǰѭ�������� ��������ǳ�ʼ��һ��ֵ û������
		int len = 0;// ���ڼ�¼�ַ����� ������100*2,���¼��lenΪ3 ��ʱ���ȡ�ַ�����ǰ��λ��������
		for (int i = 0; i < charArr.length; i++) {// ��ʼ����

			ch = charArr[i]; // ���浱ǰ��������
			if (Character.isDigit(ch)) { // �����ǰ����Ϊ ����
				len++;
			} else if (Character.isLetter(ch)) {// �����ǰ����Ϊ ��ĸ
				len++;
			} else if (ch == '.') {// �����ǰ����Ϊ . �������С������
				len++;
			} else if (Character.isSpaceChar(ch)) {// �����ǰ����Ϊ �ո� ֧�ֱ��ʽ���пո����
				if (len > 0) {// ��Ϊ�ո� ���� һ�ν��� ���Ϳ����������� ������ ������100 * 2 100�����пո�
								// �Ϳ��Խ��ո�֮ǰ�Ĵ�������ˡ�
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));// �����д����ȡ���ַ���
					len = 0;// �����ÿ�
				}
				continue;// ����ո���֣���һ�ν��� ��������ѭ��
			} else if (standard.indexOf(ch) != -1) { // ����������׼�е� ����һ������
				if (len > 0) { // ����Ҳ��
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));// ˵������֮ǰ�Ŀ��Խ�ȡ����������
					len = 0;// �����ÿ�
				}
				if (ch == '(') {// �����������
					stack.add(ch);// �������� ����ջ��
					continue; // ��������ѭ�� ��������һ��λ��
				}
				if (!stack.isEmpty()) {// ���ջ��Ϊempty
					int size = stack.size() - 1;// ��ȡջ�Ĵ�С-1 ������ջ���һ��Ԫ�ص��±�
					boolean flag = false;
					while (size >= 0 && ch == ')' && stack.get(size) != '(') {
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
					while (size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)) {
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
				}
				if (ch != ')') {
					stack.add(ch);
				} else {
					stack.remove(stack.size() - 1);
				}
			}
			if (i == charArr.length - 1) {
				if (len > 0) {
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len + 1, i + 1)));
				}
				int size = stack.size() - 1;
				while (size >= 0) {
					queue.add(String.valueOf(stack.remove(size)));
					size--;
				}
			}

		}
		return queue.stream().collect(Collectors.joining(","));
	}

	/**
	 * �� ��׺���ʽ ���� ���� ��������
	 */
	public String dealEquation(String equation) {
		if(equation==null)
			return null;
		String[] arr = equation.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			int size = list.size();
			switch (arr[i]) {
			case "+":
				int a = Integer.parseInt(list.remove(size - 2)) + Integer.parseInt(list.remove(size - 2));
				list.add(String.valueOf(a));
				break;
			case "-":
				int b1 = Integer.parseInt(list.remove(size - 2));
				int b2 = Integer.parseInt(list.remove(size - 2));
				if (b1 - b2 >= 0) {
					int b = b1 - b2;
					list.add(String.valueOf(b));
				} else
					return null;
				break;
			case "*":
				int c = Integer.parseInt(list.remove(size - 2)) * Integer.parseInt(list.remove(size - 2));
				list.add(String.valueOf(c));
				break;
			case "/":
				int d1 = Integer.parseInt(list.remove(size - 2));
				int d2 = Integer.parseInt(list.remove(size - 2));
				if (d2 != 0 && d1 % d2 == 0) {
					int d = d1 / d2;
					list.add(String.valueOf(d));
				} else
					return null;
				break;
			default:
				list.add(arr[i]);
				break;
			}
		}
		return list.size() == 1 ? list.get(0) : null;
	}
}