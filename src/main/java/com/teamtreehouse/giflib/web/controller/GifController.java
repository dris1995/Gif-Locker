package com.teamtreehouse.giflib.web.controller;

import com.teamtreehouse.giflib.model.Gif;
import com.teamtreehouse.giflib.service.CategoryService;
import com.teamtreehouse.giflib.service.GifService;
import com.teamtreehouse.giflib.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class GifController {

    @Autowired
    private GifService gifService;

    @Autowired
    private CategoryService categoryService;

    // Home page - index of all GIFs
    @RequestMapping("/")
    public String listGifs(Model model) {
        //  Get all gifs
        List<Gif> gifs = gifService.findAll();

        model.addAttribute("gifs", gifs);
        return "gif/index";
    }

    // Single GIF page
    @RequestMapping("/gifs/{gifId}")
    public String gifDetails(@PathVariable Long gifId, Model model) {
        // Get gif whose id is gifId
        Gif gif = gifService.findById(gifId);

        model.addAttribute("gif", gif);
        return "gif/details";
    }

    // GIF image data
    @RequestMapping("/gifs/{gifId}.gif")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long gifId) {
        // Return image data as byte array of the GIF whose id is gifId
        return gifService.findById(gifId).getBytes();
    }

    // Favorites - index of all GIFs marked favorite
    @RequestMapping("/favorites")
    public String favorites(Model model) {
        // TODO: Get list of all GIFs marked as favorite
        List<Gif> faves = gifService.getFavoriteGifs();

        model.addAttribute("gifs",faves);
        model.addAttribute("username","Idris Bowman"); // Static username
        return "gif/favorites";
    }

    // Upload a new GIF
    @RequestMapping(value = "/gifs", method = RequestMethod.POST)
    public String addGif(Gif gif, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes, BindingResult result) {
        //  Upload new GIF if data is valid
        // TODO: Upload new GIF if data is valid
        gifService.save(gif,file);

        // Add flash message for success
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("GIF successfully uploaded!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to new GIF's detail view
        return String.format("redirect:/gifs/%s",gif.getId());
    }

    // Form for uploading a new GIF
    @RequestMapping("/upload")
    public String formNewGif(Model model) {
        //  Add model attributes needed for new GIF upload form
        if (!model.containsAttribute("gif")){
            model.addAttribute("gif",new Gif());
        }
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("action","/gifs");
        model.addAttribute("heading","Upload");
        model.addAttribute("submit","Add");

        return "gif/form";
    }

    // Form for editing an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/edit")
    public String formEditGif(@PathVariable Long gifId, Model model) {
        // TODO: Add model attributes needed for edit form
        if (!model.containsAttribute("gif")){
            model.addAttribute("gif",gifService.findById(gifId));
        }

        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("action", String.format("/gifs/%s",gifId));
        model.addAttribute("heading","Edit GIF");
        model.addAttribute("submit","Update");

        return "gif/form";
    }

    // Update an existing GIF
    @RequestMapping(value = "/gifs/{gifId}", method = RequestMethod.POST)
    public String updateGif(@Valid Gif gif, RedirectAttributes redirectAttributes, BindingResult result) {
        // TODO: Update GIF if data is valid
         if (result.hasErrors()){
            //  Include validation errors upon redirects
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category",result);

            //  Add category if invalid was recieved(will be available for one  redirect)
            redirectAttributes.addFlashAttribute("gif", gif);

            //  Redirect back to the form (which is a request to the uri that is need to render the form)
            return String.format("redirect:/gifs/%s/edit",gif.getId());
        }

        //  Save the new category to the database
        //gifService.save(gif);

        //  Flash message that lets the user know a category was save sucessfully
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category successfully updated",FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to updated GIF's detail view
        return "gif/details";
    }

    // Delete an existing GIF
    @RequestMapping(value = "/gifs/{gifId}/delete", method = RequestMethod.POST)
    public String deleteGif(@PathVariable Long gifId,RedirectAttributes redirectAttributes) {
        Gif gif = gifService.findById(gifId);
        // TODO: Delete the GIF whose id is gifId
        gifService.delete(gif);

        //  Flash message that lets the user know a category was save sucessfully
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("Category successfully deleted",FlashMessage.Status.SUCCESS));

        // TODO: Redirect to app root
        return "redirect:/";
    }

    // Mark/unmark an existing GIF as a favorite
    @RequestMapping(value = "/gifs/{gifId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long gifId, HttpServletRequest request) {
        // TODO: With GIF whose id is gifId, toggle the favorite field
        Gif gif = gifService.findById(gifId);
        gifService.toggleFavorite(gif);

        // TODO: Redirect to GIF's detail view
        return String.format("redirect:%s",request.getHeader("referer") );
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
        // TODO: Get list of GIFs whose description contains value specified by q
        List<Gif> gifs = gifService.searchByName(q);

        model.addAttribute("gifs",gifs);
        return "gif/index";
    }
}