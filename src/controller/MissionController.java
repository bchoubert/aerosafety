package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import dao.ActionService;
import dao.IndicatorService;
import dao.LearnerService;
import dao.MissionService;
import metier.Inscription;
import metier.Mission;

@Controller
public class MissionController extends MultiActionController {

	// TODO Adapt these functions to the Service class
	// TODO Manage errors (error page? Flashbag?)

	@RequestMapping(value = "addMission.htm")
	public ModelAndView addMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		MissionService aService = new MissionService();
		if (id != null) {
			request.setAttribute("mission", aService.find(Integer.parseInt(id)));
		}

		ActionService acService = new ActionService();
		request.setAttribute("actions", acService.findAll());

		LearnerService lService = new LearnerService();
		request.setAttribute("learners", lService.findAll());

		return new ModelAndView("Mission/add");
	}

	@RequestMapping(value = "addValidateMission.htm")
	public ModelAndView createMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Mission mis = new Mission();

		mis.setWording(request.getParameter("wording"));

		MissionService mService = new MissionService();

		if (request.getParameterValues("actions") != null) {
			ActionService aService = new ActionService();
			for (String s : request.getParameterValues("actions")) {
				mis.getActions().add(aService.find(Integer.parseInt(s)));
			}
		}
		if (request.getParameterValues("learners") != null) {
			LearnerService lService = new LearnerService();
			for (String s : request.getParameterValues("learners")) {
				Inscription i = new Inscription();
				i.setMission(mis);
				i.setLearner(lService.find(Integer.parseInt(s)));
				mis.getInscriptions().add(i);
			}
		}

		mService.insertMission(mis);

		return listMission(request, response);
	}

	@RequestMapping(value = "detailsMission.htm")
	public ModelAndView detailsMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MissionService mService = new MissionService();
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("mission", mService.find(id));
		return new ModelAndView("Mission/details");
	}

	@RequestMapping(value = "listMission.htm")
	public ModelAndView listMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MissionService mService = new MissionService();
		request.setAttribute("missions", mService.findAll());
		return new ModelAndView("Mission/list");
	}

	@RequestMapping(value = "deleteMission.htm")
	public ModelAndView removeMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		MissionService mService = new MissionService();
		request.setAttribute("mission", mService.find(id));
		return new ModelAndView("Mission/remove");
	}

	@RequestMapping(value = "deleteValidateMission.htm")
	public ModelAndView deleteMission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		MissionService mService = new MissionService();
		mService.delete(id);
		return new ModelAndView("Mission/list");
	}
}
