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
import metier.Action;
import metier.InscriptionAction;

@Controller
public class ActionController extends MultiActionController {

	// TODO Adapt these functions to the Service class
	// TODO Manage errors (error page? Flashbag?)

	@RequestMapping(value = "addAction.htm")
	public ModelAndView addAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ActionService aService = new ActionService();
		if (id != null) {
			request.setAttribute("action", aService.find(Integer.parseInt(id)));
		}

		request.setAttribute("actions", aService.findAll());

		MissionService mService = new MissionService();
		request.setAttribute("missions", mService.findAll());

		IndicatorService iService = new IndicatorService();
		request.setAttribute("indicators", iService.findAll());

		LearnerService lService = new LearnerService();
		request.setAttribute("learners", lService.findAll());

		return new ModelAndView("Action/add");
	}

	@RequestMapping(value = "addValidateAction.htm")
	public ModelAndView createAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionService aService = new ActionService();

		boolean isEdit = request.getParameter("id") != null;

		Action act = !isEdit ? new Action() : aService.find(Integer.parseInt(request.getParameter("id")));

		act.setWording(request.getParameter("wording"));
		act.setScoreMinimum(Integer.parseInt(request.getParameter("scoreminimum")));

		Action act2 = aService.find(Integer.parseInt(request.getParameter("fk_action")));
		act.setAction(act2);

		MissionService mService = new MissionService();
		String[] missions = request.getParameterValues("missions");
		if (missions != null) {
			for (String s : missions) {
				act.getMissions().add(mService.find(Integer.parseInt(s)));
			}
		}
		IndicatorService iService = new IndicatorService();
		String[] indicators = request.getParameterValues("indicators");
		if (indicators != null) {
			for (String i : indicators) {
				act.getIndicators().add(iService.find(Integer.parseInt(i)));
			}
		}
		// LearnerService lService = new LearnerService();
		// String[] learners = request.getParameterValues("learners");
		// if(learners != null){
		// for(String l : learners){
		// for(InscriptionAction ia : act.getInscriptionActions())
		// {
		//
		// }
		// }
		// }
		if (!isEdit)
			aService.insertAction(act);
		else
			aService.merge(act);

		listAction(request, response);
		return new ModelAndView("Action/list");
	}

	@RequestMapping(value = "detailsAction.htm")
	public ModelAndView detailsAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		ActionService aService = new ActionService();
		request.setAttribute("action", aService.find(id));

		if (aService.find(id).getAction() != null) {
			int nextId = aService.find(id).getAction().getId();
			request.setAttribute("nextAction", aService.find(nextId));
		}

		return new ModelAndView("Action/details");
	}

	@RequestMapping(value = "listAction.htm")
	public ModelAndView listAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionService aService = new ActionService();
		request.setAttribute("actions", aService.findAll());
		return new ModelAndView("Action/list");
	}

	@RequestMapping(value = "deleteAction.htm")
	public ModelAndView removeAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Service aService = new Service();
		// int id = Integer.parseInt(request.getParameter("id"));
		// request.setAttribute("myAction", aService.detailsAction(id));
		return new ModelAndView("Action/remove");
	}

	@RequestMapping(value = "deleteValidateAction.htm")
	public ModelAndView deleteAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Service aService = new Service();
		// int id = Integer.parseInt(request.getParameter("id"));
		// aService.deleteAction(id);
		return new ModelAndView("Action/list");
	}

}
