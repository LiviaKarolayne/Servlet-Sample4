

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private Connection connection;
    public Servlet() {
        super();
        try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/agendamento", "root", "");
			System.out.println("conexão aceita");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("conexão recusada");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("conexão recusada");
		}
    }
    
    Agendamento agendado;
	Validacao validator = new Validacao();

	String nome, cpf, tel, end, data, sql;
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {
			int numAgendamento = 1;
			sql = "select * from agendamento";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			//boolean vazio = rs.next();
			if (rs.next() == false) {
				writer.println("Não há dados há serem preenchidos <br></br>");				
			}
			
			while (rs.next()) {
				agendado = new Agendamento();
				agendado.setId(rs.getInt("id"));
				agendado.setNome(rs.getString("nome"));
				agendado.setCpf(rs.getString("cpf"));
				agendado.setData(rs.getString("data"));
				agendado.setTelefone(rs.getInt("telefone"));
				agendado.setEndereco(rs.getString("endereco"));
				
				
				writer.println("Agemento "+ numAgendamento +":<br></br>"+
							   "id: " + agendado.getId() + "<br></br>"+
							   "Nome: " + agendado.getNome() + "<br></br>"+
							   "CPF: " + agendado.getCpf() + "<br></br>"+
							   "Data: " + agendado.getData() + "<br></br>"+
							   "Telefone: " + agendado.getTelefone() + "<br></br>"+
							   "Endereco: " + agendado.getEndereco() + "<br></br><br></br>");
				numAgendamento++;
			}
			writer.println("<a href='http://localhost:8080/Atividade6LiviaKarolayne/index.html'>Inserir Dados</a>");
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			writer.println("Problema na conexão dos dados.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		nome = request.getParameter("nome");
		cpf = request.getParameter("cpf");
		tel = request.getParameter("tel");
		end = request.getParameter("end");
		data = request.getParameter("data");

		agendado = new Agendamento();
		PrintWriter writer = response.getWriter();

		if (validator.vazio(nome) && validator.vazio(cpf) && validator.vazio(tel) && validator.vazio(end)
				&& validator.vazio(data)) {
			if (validator.validar_cpf(cpf)) {
				if (validator.validar_data(data)) {
					if (validator.validar_telefone(tel)) {
						agendado.setCpf(cpf);
						agendado.setData(data);
						agendado.setEndereco(end);
						agendado.setNome(nome);
						agendado.setTelefone(Integer.parseInt(tel));

						try {
							sql = "insert into agendamento(nome, cpf, data, telefone, endereco) values (?,?,?,?,?)";
							PreparedStatement ps = connection.prepareStatement(sql);
							ps.setString(1, agendado.getNome());
							ps.setString(2, agendado.getCpf());
							ps.setString(3, agendado.getData());
							ps.setInt(4, agendado.getTelefone());
							ps.setString(5, agendado.getEndereco());

							ps.executeUpdate();
							writer.println("AGENDADO COM SUCESSO!" + "<br></br>");
							writer.println("<a href='http://localhost:8080/Atividade6LiviaKarolayne/Servlet?'>Listar Dados</a>");
							ps.close();
						} catch (Exception e) {
							e.printStackTrace();
							writer.println("Hocorreu um problema na conexão ao banco de dados.");
						}

					} else {
						writer.println("TELEFONE inválido");
					}
				} else {
					writer.println("DATA inválido");
				}
			} else {
				writer.println("CPF inválido");
			}
		} else {
			writer.println("Preencha todos os campos, por favor!");
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
