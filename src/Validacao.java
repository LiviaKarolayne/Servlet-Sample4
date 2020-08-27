
public class Validacao {
	public boolean vazio(String string) {
		if (!(string.equals("") || string.equals(null))) {
			return true;
		}return false;
	}
	
	public boolean validar_telefone(String telefone) {
		try {
			Integer.parseInt(telefone);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validar_cpf(String cpf) {
		
		if (cpf.length()==14) {
			if(cpf.charAt(3)=='.' && cpf.charAt(7)=='.' && cpf.charAt(11)=='-') {
				return true;
			}
		}return false;
		
	}
	
	public boolean validar_data(String data) {
		if (data.length()==10) {
			if(data.charAt(2)=='/' && data.charAt(5)=='/') {
				return true;
			}
		}return false;
	}
}
