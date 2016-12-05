package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.cefetrj.webdep.model.entity.RegistroLogAcesso;
import br.cefetrj.webdep.model.entity.Sistema;
import br.cefetrj.webdep.model.entity.Versao;
import br.cefetrj.webdep.services.RegistroLogAcessoService;
import br.cefetrj.webdep.services.SistemaServices;
import br.cefetrj.webdep.services.VersionServices;

public class GetHttpReportListsCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String idSistema = (String) session.getAttribute("idsistema");
		long idSistemaLong = (long) session.getAttribute("idsistema");
		
		List<Versao> vers = new VersionServices().searchVersion(idSistema);
		
		Sistema sis = new SistemaServices().SearchById(idSistemaLong);
		List<RegistroLogAcesso> regLog = new RegistroLogAcessoService().searchRegistroLogAcessoBySistema(sis);		
		
		request.setAttribute("versionList", vers);
		
		List<RegistroLogAcesso> codeOk = new ArrayList<RegistroLogAcesso>();
		List<RegistroLogAcesso> codeError = new ArrayList<RegistroLogAcesso>();
		
		for(int i=0; i < regLog.size(); i++) {
			if(regLog.get(i).getCodigo() >= 400){
				codeError.add(regLog.get(i));
			} else {
				codeOk.add(regLog.get(i));
			}
		}
		
		request.setAttribute("errorList", codeError);
		request.setAttribute("okList", codeOk);
		
		request.getRequestDispatcher("HTTPreport.jsp").forward(request, response);
		
		
		/*List<Versao> vers = new VersionServices().listAllVersions();
		request.setAttribute("versionList", vers);
		
		List<RegistroLogAcesso> codeOk = new ArrayList<RegistroLogAcesso>();
		List<RegistroLogAcesso> codeError = new ArrayList<RegistroLogAcesso>();
		List<RegistroLogAcesso> regLog = new RegistroLogAcessoService().listAllRegisters();
		
		for(int i=0; i < regLog.size(); i++) {
			if(regLog.get(i).getCodigo() >= 400){
				codeError.add(regLog.get(i));
			} else {
				codeOk.add(regLog.get(i));
			}
		}
		
		request.setAttribute("errorList", codeError);
		request.setAttribute("okList", codeOk);
		
		request.getRequestDispatcher("HTTPreport.jsp").forward(request, response);*/
	}
	
}
