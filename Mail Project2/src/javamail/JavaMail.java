package javamail;

public class JavaMail {

	public static void main(String[] args) {
		String mailId="gayassheriff1311@gmail.com";
		String pass="Stuart Gayas@786";
		String subject="My Mail Project Demo";
		String content="Hey Buddy Successfully completed My Mail Project";
		JavaMailUtility.sendFromGmail(mailId, pass, subject, content);
	}

}
