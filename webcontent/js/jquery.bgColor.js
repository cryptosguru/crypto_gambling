/**
 *  jQuery Plugin bgColor (jquery.offput.ca/bgColor)
 *  (c) 2006 Blair Mitchelmore (offput.ca) blair@offput.ca
 */
/**
 * This is version 0.1 of my bgColor plugin and will likely be the final version
 * given how simple the plugin is. This plug-in returns a string in rgb syntax
 * form in most situations. 
 *
 * The main purpose of this plugin is to determine what the actual background
 * colour of an element is when the background is inherited from a parent
 * element. It works by traversing up the chain of elements until it finds an 
 * element that has a real background-color style set. It also has the
 * capability to test the element against a user provided test function
 * and return the value of a user-defined function when provided. This
 * was built into the system to allow special cases to be excluded or
 * included from the tests.
 *
 * The user-defined capabilities are very extensible. For example, if one
 * wished to find the background colour of an element but ignore any 'a'
 * elements that have an href ending with jpeg you could provide the
 * following function as the first argument of your bgColor call
 *   function(e) { return $(e).is("a[@href$='jpeg']"); }
 *
 * It won't be used by many people but I prefer (to paraphrase Larry Wall)
 * to make simple things easy and complicated things possible.
 *
 * Usage:
 *  // get the background
 *  var background = $('div p + p > strong').bgColor();
 *
 * Changelog:
 *    
 *    0.1:
 *        - Initial Release
 * 
 * @name            bgColor
 * @author          Blair Mitchelmore (blair@offput.ca)
 * @version         0.1
 */
jQuery.fn.bgColor = function(test,value) {
	var self = jQuery(this), bgColor;
	if (!test || test.constructor != Function) test = function(e) { return false; };
	if (!value || value.constructor != Function) value = function(e) { return jQuery(e).css('backgroundColor'); };
	while (true) {
		bgColor = self.css('backgroundColor');
		if (bgColor != '' && bgColor != 'transparent') break;
		if (test(self[0])) {
			bgColor = value(self[0]);
			break;
		}
		if (self.parent()[0] == document) break;	
		else self = self.parent();
	};
	if (bgColor == '' || bgColor == 'transparent') bgColor = "rgb(255,255,255)";
	return bgColor;
};
