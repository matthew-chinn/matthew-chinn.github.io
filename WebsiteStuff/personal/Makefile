.PHONY: style javascript

all: style erb javascript

style: style/style.scss style/animation.scss
	sass style/style.scss > style.css
	sass style/animation.scss >> style.css

erb: website.html.erb website.rb
	ruby website.rb > index.html

javascript: javascript/*.js
	java -jar ../compiler.jar --js_output_file=script.js javascript/cookie_animation.js javascript/transitional_animation.js 

cleanjs:
	rm script.js

clean:
	rm style.css index.html script.js
