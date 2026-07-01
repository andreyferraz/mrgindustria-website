package com.mrgindustria.website.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mrgindustria.website.model.Areas;
import com.mrgindustria.website.model.Marcas;
import com.mrgindustria.website.model.Servicos;
import com.mrgindustria.website.service.AdminService;
import com.mrgindustria.website.service.AreasService;
import com.mrgindustria.website.service.MarcasService;
import com.mrgindustria.website.service.ServicosService;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

	private static final String REDIRECT_ADMIN = "redirect:/admin";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";

	private final AdminService adminService;
	private final AreasService areasService;
	private final MarcasService marcasService;
	private final ServicosService servicosService;

	public AdminPageController(AdminService adminService,
			AreasService areasService,
			MarcasService marcasService,
			ServicosService servicosService) {
		this.adminService = adminService;
		this.areasService = areasService;
		this.marcasService = marcasService;
		this.servicosService = servicosService;
	}

	@GetMapping
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("adminUsername", principal.getName());
		model.addAttribute("areas", toList(areasService.findAll()));
		model.addAttribute("marcas", toList(marcasService.findAll()));
		model.addAttribute("servicos", toList(servicosService.findAll()));
		return "admin";
	}

	@PostMapping("/password")
	public String changePassword(Principal principal,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirectAttributes) {
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute(ERROR, "A nova senha e a confirmação não coincidem.");
			return "redirect:/admin#security";
		}

		try {
			adminService.changePasswordByUsername(principal.getName(), currentPassword, newPassword);
			redirectAttributes.addFlashAttribute(SUCCESS, "Senha alterada com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}

		return REDIRECT_ADMIN;
	}

	@PostMapping("/areas")
	public String createArea(@ModelAttribute Areas area, RedirectAttributes redirectAttributes) {
		try {
			areasService.create(area);
			redirectAttributes.addFlashAttribute(SUCCESS, "Área cadastrada com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/areas/{id}")
	public String updateArea(@PathVariable UUID id, @ModelAttribute Areas area, RedirectAttributes redirectAttributes) {
		try {
			areasService.update(id, area);
			redirectAttributes.addFlashAttribute(SUCCESS, "Área atualizada com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/areas/{id}/delete")
	public String deleteArea(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
		try {
			areasService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, "Área excluída com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/marcas")
	public String createMarca(@ModelAttribute Marcas marca,
			@RequestParam("imagemFile") MultipartFile imagemFile,
			RedirectAttributes redirectAttributes) {
		try {
			marcasService.create(marca, imagemFile);
			redirectAttributes.addFlashAttribute(SUCCESS, "Marca cadastrada com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/marcas/{id}")
	public String updateMarca(@PathVariable UUID id,
			@ModelAttribute Marcas marca,
			@RequestParam(name = "imagemFile", required = false) MultipartFile imagemFile,
			RedirectAttributes redirectAttributes) {
		try {
			marcasService.update(id, marca, imagemFile);
			redirectAttributes.addFlashAttribute(SUCCESS, "Marca atualizada com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/marcas/{id}/delete")
	public String deleteMarca(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
		try {
			marcasService.deleteById(id);
			redirectAttributes.addFlashAttribute(SUCCESS, "Marca excluída com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/servicos")
	public String createServico(@ModelAttribute Servicos servico,
			@RequestParam("imagemFile") MultipartFile imagemFile,
			RedirectAttributes redirectAttributes) {
		try {
			servicosService.create(servico, imagemFile);
			redirectAttributes.addFlashAttribute(SUCCESS, "Serviço cadastrado com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/servicos/{id}")
	public String updateServico(@PathVariable UUID id,
			@ModelAttribute Servicos servico,
			@RequestParam(name = "imagemFile", required = false) MultipartFile imagemFile,
			RedirectAttributes redirectAttributes) {
		try {
			servicosService.update(id, servico, imagemFile);
			redirectAttributes.addFlashAttribute(SUCCESS, "Serviço atualizado com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/servicos/{id}/delete")
	public String deleteServico(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
		try {
			servicosService.deleteById(id);
			redirectAttributes.addFlashAttribute(SUCCESS, "Serviço excluído com sucesso.");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).toList();
	}
}
