all: style.css index.html script.js mobile_style.css

style.css: style/style.scss style/animation.scss
	sass style/style.scss > style.css
	sass style/animation.scss >> style.css

mobile_style.css: style/mobile_style.scss
	sass style/mobile_style.scss > mobile_style.css

index.html: website.html.erb website.rb
	ruby website.rb > index.html

script.js: javascript/transitional_animation.js javascript/cookie_animation.js
	java -jar ../compiler.jar --js_output_file=script.js javascript/cookie_animation.js javascript/transitional_animation.js 

cleanjs:
	rm script.js

clean:
	rm style.css index.html script.js mobile_style.css
