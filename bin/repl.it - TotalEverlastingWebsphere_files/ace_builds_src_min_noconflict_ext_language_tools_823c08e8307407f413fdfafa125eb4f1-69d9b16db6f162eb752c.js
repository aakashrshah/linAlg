__NEXT_REGISTER_CHUNK("ace_builds_src_min_noconflict_ext_language_tools_823c08e8307407f413fdfafa125eb4f1",function(){webpackJsonp([5],{344:function(e,t){ace.define("ace/snippets",["require","exports","module","ace/lib/oop","ace/lib/event_emitter","ace/lib/lang","ace/range","ace/anchor","ace/keyboard/hash_handler","ace/tokenizer","ace/lib/dom","ace/editor"],function(e,t,i){"use strict";var n=e("./lib/oop"),o=e("./lib/event_emitter").EventEmitter,r=e("./lib/lang"),s=e("./range").Range,a=e("./anchor").Anchor,c=e("./keyboard/hash_handler").HashHandler,l=e("./tokenizer").Tokenizer,h=s.comparePoints,p=function(){this.snippetMap={},this.snippetNameMap={}};(function(){n.implement(this,o),this.getTokenizer=function(){function e(e,t,i){return e=e.substr(1),/^\d+$/.test(e)&&!i.inFormatString?[{tabstopId:parseInt(e,10)}]:[{text:e}]}function t(e){return"(?:[^\\\\"+e+"]|\\\\.)"}return p.$tokenizer=new l({start:[{regex:/:/,onMatch:function(e,t,i){return i.length&&i[0].expectIf?(i[0].expectIf=!1,i[0].elseBranch=i[0],[i[0]]):":"}},{regex:/\\./,onMatch:function(e,t,i){var n=e[1];return"}"==n&&i.length?e=n:-1!="`$\\".indexOf(n)?e=n:i.inFormatString&&("n"==n?e="\n":"t"==n?e="\n":-1!="ulULE".indexOf(n)&&(e={changeCase:n,local:n>"a"})),[e]}},{regex:/}/,onMatch:function(e,t,i){return[i.length?i.shift():e]}},{regex:/\$(?:\d+|\w+)/,onMatch:e},{regex:/\$\{[\dA-Z_a-z]+/,onMatch:function(t,i,n){var o=e(t.substr(1),i,n);return n.unshift(o[0]),o},next:"snippetVar"},{regex:/\n/,token:"newline",merge:!1}],snippetVar:[{regex:"\\|"+t("\\|")+"*\\|",onMatch:function(e,t,i){i[0].choices=e.slice(1,-1).split(",")},next:"start"},{regex:"/("+t("/")+"+)/(?:("+t("/")+"*)/)(\\w*):?",onMatch:function(e,t,i){var n=i[0];return n.fmtString=e,e=this.splitRegex.exec(e),n.guard=e[1],n.fmt=e[2],n.flag=e[3],""},next:"start"},{regex:"`"+t("`")+"*`",onMatch:function(e,t,i){return i[0].code=e.splice(1,-1),""},next:"start"},{regex:"\\?",onMatch:function(e,t,i){i[0]&&(i[0].expectIf=!0)},next:"start"},{regex:"([^:}\\\\]|\\\\.)*:?",token:"",next:"start"}],formatString:[{regex:"/("+t("/")+"+)/",token:"regex"},{regex:"",onMatch:function(e,t,i){i.inFormatString=!0},next:"start"}]}),p.prototype.getTokenizer=function(){return p.$tokenizer},p.$tokenizer},this.tokenizeTmSnippet=function(e,t){return this.getTokenizer().getLineTokens(e,t).tokens.map(function(e){return e.value||e})},this.$getDefaultValue=function(e,t){if(/^[A-Z]\d+$/.test(t)){var i=t.substr(1);return(this.variables[t[0]+"__"]||{})[i]}if(/^\d+$/.test(t))return(this.variables.__||{})[t];t=t.replace(/^TM_/,"");if(!e)return;var n=e.session;switch(t){case"CURRENT_WORD":var o=n.getWordRange();case"SELECTION":case"SELECTED_TEXT":return n.getTextRange(o);case"CURRENT_LINE":return n.getLine(e.getCursorPosition().row);case"PREV_LINE":return n.getLine(e.getCursorPosition().row-1);case"LINE_INDEX":return e.getCursorPosition().column;case"LINE_NUMBER":return e.getCursorPosition().row+1;case"SOFT_TABS":return n.getUseSoftTabs()?"YES":"NO";case"TAB_SIZE":return n.getTabSize();case"FILENAME":case"FILEPATH":return"";case"FULLNAME":return"Ace"}},this.variables={},this.getVariableValue=function(e,t){return this.variables.hasOwnProperty(t)?this.variables[t](e,t)||"":this.$getDefaultValue(e,t)||""},this.tmStrFormat=function(e,t,i){var n=t.flag||"",o=t.guard;o=new RegExp(o,n.replace(/[^gi]/,""));var r=this.tokenizeTmSnippet(t.fmt,"formatString"),s=this,a=e.replace(o,function(){s.variables.__=arguments;var e=s.resolveVariables(r,i),t="E";for(var n=0;n<e.length;n++){var o=e[n];if("object"==typeof o){e[n]="";if(o.changeCase&&o.local){var a=e[n+1];a&&"string"==typeof a&&("u"==o.changeCase?e[n]=a[0].toUpperCase():e[n]=a[0].toLowerCase(),e[n+1]=a.substr(1))}else o.changeCase&&(t=o.changeCase)}else"U"==t?e[n]=o.toUpperCase():"L"==t&&(e[n]=o.toLowerCase())}return e.join("")});return this.variables.__=null,a},this.resolveVariables=function(e,t){function i(t){var i=e.indexOf(t,o+1);-1!=i&&(o=i)}var n=[];for(var o=0;o<e.length;o++){var r=e[o];if("string"==typeof r)n.push(r);else{if("object"!=typeof r)continue;if(r.skip)i(r);else{if(r.processed<o)continue;if(r.text){var s=this.getVariableValue(t,r.text);s&&r.fmtString&&(s=this.tmStrFormat(s,r)),r.processed=o,null==r.expectIf?s&&(n.push(s),i(r)):s?r.skip=r.elseBranch:i(r)}else null!=r.tabstopId?n.push(r):null!=r.changeCase&&n.push(r)}}}return n},this.insertSnippetForSelection=function(e,t){function i(e){var t=[];for(var i=0;i<e.length;i++){var n=e[i];if("object"==typeof n){if(l[n.tabstopId])continue;var o=e.lastIndexOf(n,i-1);n=t[o]||{tabstopId:n.tabstopId}}t[i]=n}return t}var n=e.getCursorPosition(),o=e.session.getLine(n.row),r=e.session.getTabString(),s=o.match(/^\s*/)[0];n.column<s.length&&(s=s.slice(0,n.column)),t=t.replace(/\r/g,"");var a=this.tokenizeTmSnippet(t);a=this.resolveVariables(a,e),a=a.map(function(e){return"\n"==e?e+s:"string"==typeof e?e.replace(/\t/g,r):e});var c=[];a.forEach(function(e,t){if("object"!=typeof e)return;var i=e.tabstopId,n=c[i];n||(n=c[i]=[],n.index=i,n.value="");if(-1!==n.indexOf(e))return;n.push(e);var o=a.indexOf(e,t+1);if(-1===o)return;var r=a.slice(t+1,o),s=r.some(function(e){return"object"==typeof e});s&&!n.value?n.value=r:r.length&&(!n.value||"string"!=typeof n.value)&&(n.value=r.join(""))}),c.forEach(function(e){e.length=0});var l={};for(var h=0;h<a.length;h++){var p=a[h];if("object"!=typeof p)continue;var d=p.tabstopId,f=a.indexOf(p,h+1);if(l[d]){l[d]===p&&(l[d]=null);continue}var g=c[d],m="string"==typeof g.value?[g.value]:i(g.value);m.unshift(h+1,Math.max(0,f-h)),m.push(p),l[d]=p,a.splice.apply(a,m),-1===g.indexOf(p)&&g.push(p)}var v=0,b=0,x="";a.forEach(function(e){if("string"==typeof e){var t=e.split("\n");t.length>1?(b=t[t.length-1].length,v+=t.length-1):b+=e.length,x+=e}else e.start?e.end={row:v,column:b}:e.start={row:v,column:b}});var w=e.getSelectionRange(),T=e.session.replace(w,x),y=new u(e),S=e.inVirtualSelectionMode&&e.selection.index;y.addTabstops(c,w.start,T,S)},this.insertSnippet=function(e,t){var i=this;if(e.inVirtualSelectionMode)return i.insertSnippetForSelection(e,t);e.forEachSelection(function(){i.insertSnippetForSelection(e,t)},null,{keepOrder:!0}),e.tabstopManager&&e.tabstopManager.tabNext()},this.$getScope=function(e){var t=e.session.$mode.$id||"";t=t.split("/").pop();if("html"===t||"php"===t){"php"===t&&!e.session.$mode.inlinePhp&&(t="html");var i=e.getCursorPosition(),n=e.session.getState(i.row);"object"==typeof n&&(n=n[0]),n.substring&&("js-"==n.substring(0,3)?t="javascript":"css-"==n.substring(0,4)?t="css":"php-"==n.substring(0,4)&&(t="php"))}return t},this.getActiveScopes=function(e){var t=this.$getScope(e),i=[t],n=this.snippetMap;return n[t]&&n[t].includeScopes&&i.push.apply(i,n[t].includeScopes),i.push("_"),i},this.expandWithTab=function(e,t){var i=this,n=e.forEachSelection(function(){return i.expandSnippetForSelection(e,t)},null,{keepOrder:!0});return n&&e.tabstopManager&&e.tabstopManager.tabNext(),n},this.expandSnippetForSelection=function(e,t){var i=e.getCursorPosition(),n=e.session.getLine(i.row),o=n.substring(0,i.column),r=n.substr(i.column),s=this.snippetMap,a;return this.getActiveScopes(e).some(function(e){var t=s[e];return t&&(a=this.findMatchingSnippet(t,o,r)),!!a},this),!!a&&(!(!t||!t.dryRun)||(e.session.doc.removeInLine(i.row,i.column-a.replaceBefore.length,i.column+a.replaceAfter.length),this.variables.M__=a.matchBefore,this.variables.T__=a.matchAfter,this.insertSnippetForSelection(e,a.content),this.variables.M__=this.variables.T__=null,!0))},this.findMatchingSnippet=function(e,t,i){for(var n=e.length;n--;){var o=e[n];if(o.startRe&&!o.startRe.test(t))continue;if(o.endRe&&!o.endRe.test(i))continue;if(!o.startRe&&!o.endRe)continue;return o.matchBefore=o.startRe?o.startRe.exec(t):[""],o.matchAfter=o.endRe?o.endRe.exec(i):[""],o.replaceBefore=o.triggerRe?o.triggerRe.exec(t)[0]:"",o.replaceAfter=o.endTriggerRe?o.endTriggerRe.exec(i)[0]:"",o}},this.snippetMap={},this.snippetNameMap={},this.register=function(e,t){function i(e){return e&&!/^\^?\(.*\)\$?$|^\\b$/.test(e)&&(e="(?:"+e+")"),e||""}function n(e,t,n){return e=i(e),t=i(t),n?(e=t+e,e&&"$"!=e[e.length-1]&&(e+="$")):(e+=t,e&&"^"!=e[0]&&(e="^"+e)),new RegExp(e)}function o(e){e.scope||(e.scope=t||"_"),t=e.scope,s[t]||(s[t]=[],a[t]={});var i=a[t];if(e.name){var o=i[e.name];o&&c.unregister(o),i[e.name]=e}s[t].push(e),e.tabTrigger&&!e.trigger&&(!e.guard&&/^\w/.test(e.tabTrigger)&&(e.guard="\\b"),e.trigger=r.escapeRegExp(e.tabTrigger));if(!e.trigger&&!e.guard&&!e.endTrigger&&!e.endGuard)return;e.startRe=n(e.trigger,e.guard,!0),e.triggerRe=new RegExp(e.trigger,"",!0),e.endRe=n(e.endTrigger,e.endGuard,!0),e.endTriggerRe=new RegExp(e.endTrigger,"",!0)}var s=this.snippetMap,a=this.snippetNameMap,c=this;e||(e=[]),e&&e.content?o(e):Array.isArray(e)&&e.forEach(o),this._signal("registerSnippets",{scope:t})},this.unregister=function(e,t){function i(e){var i=o[e.scope||t];if(i&&i[e.name]){delete i[e.name];var r=n[e.scope||t],s=r&&r.indexOf(e);s>=0&&r.splice(s,1)}}var n=this.snippetMap,o=this.snippetNameMap;e.content?i(e):Array.isArray(e)&&e.forEach(i)},this.parseSnippetFile=function(e){e=e.replace(/\r/g,"");var t=[],i={},n=/^#.*|^({[\s\S]*})\s*$|^(\S+) (.*)$|^((?:\n*\t.*)+)/gm,o;while(o=n.exec(e)){if(o[1])try{i=JSON.parse(o[1]),t.push(i)}catch(e){}if(o[4])i.content=o[4].replace(/^\t/gm,""),t.push(i),i={};else{var r=o[2],s=o[3];if("regex"==r){var a=/\/((?:[^\/\\]|\\.)*)|$/g;i.guard=a.exec(s)[1],i.trigger=a.exec(s)[1],i.endTrigger=a.exec(s)[1],i.endGuard=a.exec(s)[1]}else"snippet"==r?(i.tabTrigger=s.match(/^\S*/)[0],i.name||(i.name=s)):i[r]=s}}return t},this.getSnippetByName=function(e,t){var i=this.snippetNameMap,n;return this.getActiveScopes(t).some(function(t){var o=i[t];return o&&(n=o[e]),!!n},this),n}}).call(p.prototype);var u=function(e){if(e.tabstopManager)return e.tabstopManager;e.tabstopManager=this,this.$onChange=this.onChange.bind(this),this.$onChangeSelection=r.delayedCall(this.onChangeSelection.bind(this)).schedule,this.$onChangeSession=this.onChangeSession.bind(this),this.$onAfterExec=this.onAfterExec.bind(this),this.attach(e)};(function(){this.attach=function(e){this.index=0,this.ranges=[],this.tabstops=[],this.$openTabstops=null,this.selectedTabstop=null,this.editor=e,this.editor.on("change",this.$onChange),this.editor.on("changeSelection",this.$onChangeSelection),this.editor.on("changeSession",this.$onChangeSession),this.editor.commands.on("afterExec",this.$onAfterExec),this.editor.keyBinding.addKeyboardHandler(this.keyboardHandler)},this.detach=function(){this.tabstops.forEach(this.removeTabstopMarkers,this),this.ranges=null,this.tabstops=null,this.selectedTabstop=null,this.editor.removeListener("change",this.$onChange),this.editor.removeListener("changeSelection",this.$onChangeSelection),this.editor.removeListener("changeSession",this.$onChangeSession),this.editor.commands.removeListener("afterExec",this.$onAfterExec),this.editor.keyBinding.removeKeyboardHandler(this.keyboardHandler),this.editor.tabstopManager=null,this.editor=null},this.onChange=function(e){var t=e,i="r"==e.action[0],n=e.start,o=e.end,r=n.row,s=o.row,a=s-r,c=o.column-n.column;i&&(a=-a,c=-c);if(!this.$inChange&&i){var l=this.selectedTabstop,p=l&&!l.some(function(e){return h(e.start,n)<=0&&h(e.end,o)>=0});if(p)return this.detach()}var u=this.ranges;for(var d=0;d<u.length;d++){var f=u[d];if(f.end.row<n.row)continue;if(i&&h(n,f.start)<0&&h(o,f.end)>0){this.removeRange(f),d--;continue}f.start.row==r&&f.start.column>n.column&&(f.start.column+=c),f.end.row==r&&f.end.column>=n.column&&(f.end.column+=c),f.start.row>=r&&(f.start.row+=a),f.end.row>=r&&(f.end.row+=a),h(f.start,f.end)>0&&this.removeRange(f)}u.length||this.detach()},this.updateLinkedFields=function(){var e=this.selectedTabstop;if(!e||!e.hasLinkedRanges)return;this.$inChange=!0;var i=this.editor.session,n=i.getTextRange(e.firstNonLinked);for(var o=e.length;o--;){var r=e[o];if(!r.linked)continue;var s=t.snippetManager.tmStrFormat(n,r.original);i.replace(r,s)}this.$inChange=!1},this.onAfterExec=function(e){e.command&&!e.command.readOnly&&this.updateLinkedFields()},this.onChangeSelection=function(){if(!this.editor)return;var e=this.editor.selection.lead,t=this.editor.selection.anchor,i=this.editor.selection.isEmpty();for(var n=this.ranges.length;n--;){if(this.ranges[n].linked)continue;var o=this.ranges[n].contains(e.row,e.column),r=i||this.ranges[n].contains(t.row,t.column);if(o&&r)return}this.detach()},this.onChangeSession=function(){this.detach()},this.tabNext=function(e){var t=this.tabstops.length,i=this.index+(e||1);i=Math.min(Math.max(i,1),t),i==t&&(i=0),this.selectTabstop(i),0===i&&this.detach()},this.selectTabstop=function(e){this.$openTabstops=null;var t=this.tabstops[this.index];t&&this.addTabstopMarkers(t),this.index=e,t=this.tabstops[this.index];if(!t||!t.length)return;this.selectedTabstop=t;if(this.editor.inVirtualSelectionMode)this.editor.selection.setRange(t.firstNonLinked);else{var i=this.editor.multiSelect;i.toSingleRange(t.firstNonLinked.clone());for(var n=t.length;n--;){if(t.hasLinkedRanges&&t[n].linked)continue;i.addRange(t[n].clone(),!0)}i.ranges[0]&&i.addRange(i.ranges[0].clone())}this.editor.keyBinding.addKeyboardHandler(this.keyboardHandler)},this.addTabstops=function(e,t,i){this.$openTabstops||(this.$openTabstops=[]);if(!e[0]){var n=s.fromPoints(i,i);g(n.start,t),g(n.end,t),e[0]=[n],e[0].index=0}var o=this.index,r=[o+1,0],a=this.ranges;e.forEach(function(e,i){var n=this.$openTabstops[i]||e;for(var o=e.length;o--;){var c=e[o],l=s.fromPoints(c.start,c.end||c.start);f(l.start,t),f(l.end,t),l.original=c,l.tabstop=n,a.push(l),n!=e?n.unshift(l):n[o]=l,c.fmtString?(l.linked=!0,n.hasLinkedRanges=!0):n.firstNonLinked||(n.firstNonLinked=l)}n.firstNonLinked||(n.hasLinkedRanges=!1),n===e&&(r.push(n),this.$openTabstops[i]=n),this.addTabstopMarkers(n)},this),r.length>2&&(this.tabstops.length&&r.push(r.splice(2,1)[0]),this.tabstops.splice.apply(this.tabstops,r))},this.addTabstopMarkers=function(e){var t=this.editor.session;e.forEach(function(e){e.markerId||(e.markerId=t.addMarker(e,"ace_snippet-marker","text"))})},this.removeTabstopMarkers=function(e){var t=this.editor.session;e.forEach(function(e){t.removeMarker(e.markerId),e.markerId=null})},this.removeRange=function(e){var t=e.tabstop.indexOf(e);e.tabstop.splice(t,1),t=this.ranges.indexOf(e),this.ranges.splice(t,1),this.editor.session.removeMarker(e.markerId),e.tabstop.length||(t=this.tabstops.indexOf(e.tabstop),-1!=t&&this.tabstops.splice(t,1),this.tabstops.length||this.detach())},this.keyboardHandler=new c,this.keyboardHandler.bindKeys({Tab:function(e){if(t.snippetManager&&t.snippetManager.expandWithTab(e))return;e.tabstopManager.tabNext(1)},"Shift-Tab":function(e){e.tabstopManager.tabNext(-1)},Esc:function(e){e.tabstopManager.detach()},Return:function(e){return!1}})}).call(u.prototype);var d={};d.onChange=a.prototype.onChange,d.setPosition=function(e,t){this.pos.row=e,this.pos.column=t},d.update=function(e,t,i){this.$insertRight=i,this.pos=e,this.onChange(t)};var f=function(e,t){0==e.row&&(e.column+=t.column),e.row+=t.row},g=function(e,t){e.row==t.row&&(e.column-=t.column),e.row-=t.row};e("./lib/dom").importCssString(".ace_snippet-marker {    -moz-box-sizing: border-box;    box-sizing: border-box;    background: rgba(194, 193, 208, 0.09);    border: 1px dotted rgba(211, 208, 235, 0.62);    position: absolute;}"),t.snippetManager=new p;var m=e("./editor").Editor;(function(){this.insertSnippet=function(e,i){return t.snippetManager.insertSnippet(this,e,i)},this.expandSnippet=function(e){return t.snippetManager.expandWithTab(this,e)}}).call(m.prototype)}),ace.define("ace/autocomplete/popup",["require","exports","module","ace/virtual_renderer","ace/editor","ace/range","ace/lib/event","ace/lib/lang","ace/lib/dom"],function(e,t,i){"use strict";var n=e("../virtual_renderer").VirtualRenderer,o=e("../editor").Editor,r=e("../range").Range,s=e("../lib/event"),a=e("../lib/lang"),c=e("../lib/dom"),l=function(e){var t=new n(e);t.$maxLines=4;var i=new o(t);return i.setHighlightActiveLine(!1),i.setShowPrintMargin(!1),i.renderer.setShowGutter(!1),i.renderer.setHighlightGutterLine(!1),i.$mouseHandler.$focusWaitTimout=0,i.$highlightTagPending=!0,i},h=function(e){var t=c.createElement("div"),i=new l(t);e&&e.appendChild(t),t.style.display="none",i.renderer.content.style.cursor="default",i.renderer.setStyle("ace_autocomplete"),i.setOption("displayIndentGuides",!1),i.setOption("dragDelay",150);var n=function(){};i.focus=n,i.$isFocused=!0,i.renderer.$cursorLayer.restartTimer=n,i.renderer.$cursorLayer.element.style.opacity=0,i.renderer.$maxLines=8,i.renderer.$keepTextAreaAtCursor=!1,i.setHighlightActiveLine(!1),i.session.highlight(""),i.session.$searchHighlight.clazz="ace_highlight-marker",i.on("mousedown",function(e){var t=e.getDocumentPosition();i.selection.moveToPosition(t),p.start.row=p.end.row=t.row,e.stop()});var o,h=new r(-1,0,-1,Infinity),p=new r(-1,0,-1,Infinity);p.id=i.session.addMarker(p,"ace_active-line","fullLine"),i.setSelectOnHover=function(e){e?h.id&&(i.session.removeMarker(h.id),h.id=null):h.id=i.session.addMarker(h,"ace_line-hover","fullLine")},i.setSelectOnHover(!1),i.on("mousemove",function(e){if(!o){o=e;return}if(o.x==e.x&&o.y==e.y)return;o=e,o.scrollTop=i.renderer.scrollTop;var t=o.getDocumentPosition().row;h.start.row!=t&&(h.id||i.setRow(t),d(t))}),i.renderer.on("beforeRender",function(){if(o&&-1!=h.start.row){o.$pos=null;var e=o.getDocumentPosition().row;h.id||i.setRow(e),d(e,!0)}}),i.renderer.on("afterRender",function(){var e=i.getRow(),t=i.renderer.$textLayer,n=t.element.childNodes[e-t.config.firstRow];if(n==t.selectedNode)return;t.selectedNode&&c.removeCssClass(t.selectedNode,"ace_selected"),t.selectedNode=n,n&&c.addCssClass(n,"ace_selected")});var u=function(){d(-1)},d=function(e,t){e!==h.start.row&&(h.start.row=h.end.row=e,t||i.session._emit("changeBackMarker"),i._emit("changeHoverMarker"))};i.getHoveredRow=function(){return h.start.row},s.addListener(i.container,"mouseout",u),i.on("hide",u),i.on("changeSelection",u),i.session.doc.getLength=function(){return i.data.length},i.session.doc.getLine=function(e){var t=i.data[e];return"string"==typeof t?t:t&&t.value||""};var f=i.session.bgTokenizer;return f.$tokenizeRow=function(e){var t=i.data[e],n=[];if(!t)return n;"string"==typeof t&&(t={value:t}),t.caption||(t.caption=t.value||t.name);var o=-1,r,s;for(var a=0;a<t.caption.length;a++)s=t.caption[a],r=t.matchMask&1<<a?1:0,o!==r?(n.push({type:t.className||(r?"completion-highlight":""),value:s}),o=r):n[n.length-1].value+=s;if(t.meta){var c=i.renderer.$size.scrollerWidth/i.renderer.layerConfig.characterWidth,l=t.meta;l.length+t.caption.length>c-2&&(l=l.substr(0,c-t.caption.length-3)+"…"),n.push({type:"rightAlignedText",value:l})}return n},f.$updateOnChange=n,f.start=n,i.session.$computeWidth=function(){return this.screenWidth=0},i.$blockScrolling=Infinity,i.isOpen=!1,i.isTopdown=!1,i.autoSelect=!0,i.data=[],i.setData=function(e){i.setValue(a.stringRepeat("\n",e.length),-1),i.data=e||[],i.setRow(0)},i.getData=function(e){return i.data[e]},i.getRow=function(){return p.start.row},i.setRow=function(e){e=Math.max(this.autoSelect?0:-1,Math.min(this.data.length,e)),p.start.row!=e&&(i.selection.clearSelection(),p.start.row=p.end.row=e||0,i.session._emit("changeBackMarker"),i.moveCursorTo(e||0,0),i.isOpen&&i._signal("select"))},i.on("changeSelection",function(){i.isOpen&&i.setRow(i.selection.lead.row),i.renderer.scrollCursorIntoView()}),i.hide=function(){this.container.style.display="none",this._signal("hide"),i.isOpen=!1},i.show=function(e,t,n){var r=this.container,s=window.innerHeight,a=window.innerWidth,c=this.renderer,l=c.$maxLines*t*1.4,h=e.top+this.$borderSize,p=h>s/2&&!n;p&&h+t+l>s?(c.$maxPixelHeight=h-2*this.$borderSize,r.style.top="",r.style.bottom=s-h+"px",i.isTopdown=!1):(h+=t,c.$maxPixelHeight=s-h-.2*t,r.style.top=h+"px",r.style.bottom="",i.isTopdown=!0),r.style.display="",this.renderer.$textLayer.checkForSizeChanges();var u=e.left;u+r.offsetWidth>a&&(u=a-r.offsetWidth),r.style.left=u+"px",this._signal("show"),o=null,i.isOpen=!0},i.getTextLeftOffset=function(){return this.$borderSize+this.renderer.$padding+this.$imageSize},i.$imageSize=0,i.$borderSize=1,i};c.importCssString(".ace_editor.ace_autocomplete .ace_marker-layer .ace_active-line {    background-color: #CAD6FA;    z-index: 1;}.ace_editor.ace_autocomplete .ace_line-hover {    border: 1px solid #abbffe;    margin-top: -1px;    background: rgba(233,233,253,0.4);}.ace_editor.ace_autocomplete .ace_line-hover {    position: absolute;    z-index: 2;}.ace_editor.ace_autocomplete .ace_scroller {   background: none;   border: none;   box-shadow: none;}.ace_rightAlignedText {    color: gray;    display: inline-block;    position: absolute;    right: 4px;    text-align: right;    z-index: -1;}.ace_editor.ace_autocomplete .ace_completion-highlight{    color: #000;    text-shadow: 0 0 0.01em;}.ace_editor.ace_autocomplete {    width: 280px;    z-index: 200000;    background: #fbfbfb;    color: #444;    border: 1px lightgray solid;    position: fixed;    box-shadow: 2px 3px 5px rgba(0,0,0,.2);    line-height: 1.4;}"),t.AcePopup=h}),ace.define("ace/autocomplete/util",["require","exports","module"],function(e,t,i){"use strict";t.parForEach=function(e,t,i){var n=0,o=e.length;0===o&&i();for(var r=0;r<o;r++)t(e[r],function(e,t){n++,n===o&&i(e,t)})};var n=/[a-zA-Z_0-9\$\-\u00A2-\uFFFF]/;t.retrievePrecedingIdentifier=function(e,t,i){i=i||n;var o=[];for(var r=t-1;r>=0;r--){if(!i.test(e[r]))break;o.push(e[r])}return o.reverse().join("")},t.retrieveFollowingIdentifier=function(e,t,i){i=i||n;var o=[];for(var r=t;r<e.length;r++){if(!i.test(e[r]))break;o.push(e[r])}return o},t.getCompletionPrefix=function(e){var t=e.getCursorPosition(),i=e.session.getLine(t.row),n;return e.completers.forEach(function(e){e.identifierRegexps&&e.identifierRegexps.forEach(function(e){!n&&e&&(n=this.retrievePrecedingIdentifier(i,t.column,e))}.bind(this))}.bind(this)),n||this.retrievePrecedingIdentifier(i,t.column)}}),ace.define("ace/autocomplete",["require","exports","module","ace/keyboard/hash_handler","ace/autocomplete/popup","ace/autocomplete/util","ace/lib/event","ace/lib/lang","ace/lib/dom","ace/snippets"],function(e,t,i){"use strict";var n=e("./keyboard/hash_handler").HashHandler,o=e("./autocomplete/popup").AcePopup,r=e("./autocomplete/util"),s=e("./lib/event"),a=e("./lib/lang"),c=e("./lib/dom"),l=e("./snippets").snippetManager,h=function(){this.autoInsert=!1,this.autoSelect=!0,this.exactMatch=!1,this.gatherCompletionsId=0,this.keyboardHandler=new n,this.keyboardHandler.bindKeys(this.commands),this.blurListener=this.blurListener.bind(this),this.changeListener=this.changeListener.bind(this),this.mousedownListener=this.mousedownListener.bind(this),this.mousewheelListener=this.mousewheelListener.bind(this),this.changeTimer=a.delayedCall(function(){this.updateCompletions(!0)}.bind(this)),this.tooltipTimer=a.delayedCall(this.updateDocTooltip.bind(this),50)};(function(){this.$init=function(){return this.popup=new o(document.body||document.documentElement),this.popup.on("click",function(e){this.insertMatch(),e.stop()}.bind(this)),this.popup.focus=this.editor.focus.bind(this.editor),this.popup.on("show",this.tooltipTimer.bind(null,null)),this.popup.on("select",this.tooltipTimer.bind(null,null)),this.popup.on("changeHoverMarker",this.tooltipTimer.bind(null,null)),this.popup},this.getPopup=function(){return this.popup||this.$init()},this.openPopup=function(e,t,i){this.popup||this.$init(),this.popup.autoSelect=this.autoSelect,this.popup.setData(this.completions.filtered),e.keyBinding.addKeyboardHandler(this.keyboardHandler);var n=e.renderer;this.popup.setRow(this.autoSelect?0:-1);if(i)i&&!t&&this.detach();else{this.popup.setTheme(e.getTheme()),this.popup.setFontSize(e.getFontSize());var o=n.layerConfig.lineHeight,r=n.$cursorLayer.getPixelPosition(this.base,!0);r.left-=this.popup.getTextLeftOffset();var s=e.container.getBoundingClientRect();r.top+=s.top-n.layerConfig.offset,r.left+=s.left-e.renderer.scrollLeft,r.left+=n.gutterWidth,this.popup.show(r,o)}},this.detach=function(){this.editor.keyBinding.removeKeyboardHandler(this.keyboardHandler),this.editor.off("changeSelection",this.changeListener),this.editor.off("blur",this.blurListener),this.editor.off("mousedown",this.mousedownListener),this.editor.off("mousewheel",this.mousewheelListener),this.changeTimer.cancel(),this.hideDocTooltip(),this.gatherCompletionsId+=1,this.popup&&this.popup.isOpen&&this.popup.hide(),this.base&&this.base.detach(),this.activated=!1,this.completions=this.base=null},this.changeListener=function(e){var t=this.editor.selection.lead;(t.row!=this.base.row||t.column<this.base.column)&&this.detach(),this.activated?this.changeTimer.schedule():this.detach()},this.blurListener=function(e){var t=document.activeElement,i=this.editor.textInput.getElement(),n=e.relatedTarget&&this.tooltipNode&&this.tooltipNode.contains(e.relatedTarget),o=this.popup&&this.popup.container;t!=i&&t.parentNode!=o&&!n&&t!=this.tooltipNode&&e.relatedTarget!=i&&this.detach()},this.mousedownListener=function(e){this.detach()},this.mousewheelListener=function(e){this.detach()},this.goTo=function(e){var t=this.popup.getRow(),i=this.popup.session.getLength()-1;switch(e){case"up":t=t<=0?i:t-1;break;case"down":t=t>=i?-1:t+1;break;case"start":t=0;break;case"end":t=i}this.popup.setRow(t)},this.insertMatch=function(e,t){e||(e=this.popup.getData(this.popup.getRow()));if(!e)return!1;if(e.completer&&e.completer.insertMatch)e.completer.insertMatch(this.editor,e);else{if(this.completions.filterText){var i=this.editor.selection.getAllRanges();for(var n=0,o;o=i[n];n++)o.start.column-=this.completions.filterText.length,this.editor.session.remove(o)}e.snippet?l.insertSnippet(this.editor,e.snippet):this.editor.execCommand("insertstring",e.value||e)}this.detach()},this.commands={Up:function(e){e.completer.goTo("up")},Down:function(e){e.completer.goTo("down")},"Ctrl-Up|Ctrl-Home":function(e){e.completer.goTo("start")},"Ctrl-Down|Ctrl-End":function(e){e.completer.goTo("end")},Esc:function(e){e.completer.detach()},Return:function(e){return e.completer.insertMatch()},"Shift-Return":function(e){e.completer.insertMatch(null,{deleteSuffix:!0})},Tab:function(e){var t=e.completer.insertMatch();if(!!t||!!e.tabstopManager)return t;e.completer.goTo("down")},PageUp:function(e){e.completer.popup.gotoPageUp()},PageDown:function(e){e.completer.popup.gotoPageDown()}},this.gatherCompletions=function(e,t){var i=e.getSession(),n=e.getCursorPosition(),o=r.getCompletionPrefix(e);this.base=i.doc.createAnchor(n.row,n.column-o.length),this.base.$insertRight=!0;var s=[],a=e.completers.length;return e.completers.forEach(function(c,l){c.getCompletions(e,i,n,o,function(i,n){!i&&n&&(s=s.concat(n)),t(null,{prefix:r.getCompletionPrefix(e),matches:s,finished:0===--a})})}),!0},this.showPopup=function(e){this.editor&&this.detach(),this.activated=!0,this.editor=e,e.completer!=this&&(e.completer&&e.completer.detach(),e.completer=this),e.on("changeSelection",this.changeListener),e.on("blur",this.blurListener),e.on("mousedown",this.mousedownListener),e.on("mousewheel",this.mousewheelListener),this.updateCompletions()},this.updateCompletions=function(e){if(e&&this.base&&this.completions){var t=this.editor.getCursorPosition(),i=this.editor.session.getTextRange({start:this.base,end:t});if(i==this.completions.filterText)return;this.completions.setFilter(i);if(!this.completions.filtered.length)return this.detach();if(1==this.completions.filtered.length&&this.completions.filtered[0].value==i&&!this.completions.filtered[0].snippet)return this.detach();this.openPopup(this.editor,i,e);return}var n=this.gatherCompletionsId;this.gatherCompletions(this.editor,function(t,i){var o=function(){if(!i.finished)return;return this.detach()}.bind(this),r=i.prefix,s=i&&i.matches;if(!s||!s.length)return o();if(0!==r.indexOf(i.prefix)||n!=this.gatherCompletionsId)return;this.completions=new p(s),this.exactMatch&&(this.completions.exactMatch=!0),this.completions.setFilter(r);var a=this.completions.filtered;if(!a.length)return o();if(1==a.length&&a[0].value==r&&!a[0].snippet)return o();if(this.autoInsert&&1==a.length&&i.finished)return this.insertMatch(a[0]);this.openPopup(this.editor,r,e)}.bind(this))},this.cancelContextMenu=function(){this.editor.$mouseHandler.cancelContextMenu()},this.updateDocTooltip=function(){var e=this.popup,t=e.data,i=t&&(t[e.getHoveredRow()]||t[e.getRow()]),n=null;if(!i||!this.editor||!this.popup.isOpen)return this.hideDocTooltip();this.editor.completers.some(function(e){return e.getDocTooltip&&(n=e.getDocTooltip(i)),n}),n||(n=i),"string"==typeof n&&(n={docText:n});if(!n||!n.docHTML&&!n.docText)return this.hideDocTooltip();this.showDocTooltip(n)},this.showDocTooltip=function(e){this.tooltipNode||(this.tooltipNode=c.createElement("div"),this.tooltipNode.className="ace_tooltip ace_doc-tooltip",this.tooltipNode.style.margin=0,this.tooltipNode.style.pointerEvents="auto",this.tooltipNode.tabIndex=-1,this.tooltipNode.onblur=this.blurListener.bind(this),this.tooltipNode.onclick=this.onTooltipClick.bind(this));var t=this.tooltipNode;e.docHTML?t.innerHTML=e.docHTML:e.docText&&(t.textContent=e.docText),t.parentNode||document.body.appendChild(t);var i=this.popup,n=i.container.getBoundingClientRect();t.style.top=i.container.style.top,t.style.bottom=i.container.style.bottom,window.innerWidth-n.right<320?(t.style.right=window.innerWidth-n.left+"px",t.style.left=""):(t.style.left=n.right+1+"px",t.style.right=""),t.style.display="block"},this.hideDocTooltip=function(){this.tooltipTimer.cancel();if(!this.tooltipNode)return;var e=this.tooltipNode;!this.editor.isFocused()&&document.activeElement==e&&this.editor.focus(),this.tooltipNode=null,e.parentNode&&e.parentNode.removeChild(e)},this.onTooltipClick=function(e){var t=e.target;while(t&&t!=this.tooltipNode){if("A"==t.nodeName&&t.href){t.rel="noreferrer",t.target="_blank";break}t=t.parentNode}}}).call(h.prototype),h.startCommand={name:"startAutocomplete",exec:function(e){e.completer||(e.completer=new h),e.completer.autoInsert=!1,e.completer.autoSelect=!0,e.completer.showPopup(e),e.completer.cancelContextMenu()},bindKey:"Ctrl-Space|Ctrl-Shift-Space|Alt-Space"};var p=function(e,t){this.all=e,this.filtered=e,this.filterText=t||"",this.exactMatch=!1};(function(){this.setFilter=function(e){if(e.length>this.filterText&&0===e.lastIndexOf(this.filterText,0))var t=this.filtered;else var t=this.all;this.filterText=e,t=this.filterCompletions(t,this.filterText),t=t.sort(function(e,t){return t.exactMatch-e.exactMatch||t.score-e.score});var i=null;t=t.filter(function(e){var t=e.snippet||e.caption||e.value;return t!==i&&(i=t,!0)}),this.filtered=t},this.filterCompletions=function(e,t){var i=[],n=t.toUpperCase(),o=t.toLowerCase();e:for(var r=0,s;s=e[r];r++){var a=s.value||s.caption||s.snippet;if(!a)continue;var c=-1,l=0,h=0,p,u;if(this.exactMatch){if(t!==a.substr(0,t.length))continue e}else for(var d=0;d<t.length;d++){var f=a.indexOf(o[d],c+1),g=a.indexOf(n[d],c+1);p=f>=0&&(g<0||f<g)?f:g;if(p<0)continue e;u=p-c-1,u>0&&(-1===c&&(h+=10),h+=u),l|=1<<p,c=p}s.matchMask=l,s.exactMatch=h?0:1,s.score=(s.score||0)-h,i.push(s)}return i}}).call(p.prototype),t.Autocomplete=h,t.FilteredList=p}),ace.define("ace/autocomplete/text_completer",["require","exports","module","ace/range"],function(e,t,i){function n(e,t){var i=e.getTextRange(r.fromPoints({row:0,column:0},t));return i.split(s).length-1}function o(e,t){var i=n(e,t),o=e.getValue().split(s),r=Object.create(null),a=o[i];return o.forEach(function(e,t){if(!e||e===a)return;var n=Math.abs(i-t),s=o.length-n;r[e]?r[e]=Math.max(s,r[e]):r[e]=s}),r}var r=e("../range").Range,s=/[^a-zA-Z_0-9\$\-\u00C0-\u1FFF\u2C00-\uD7FF\w]+/;t.getCompletions=function(e,t,i,n,r){var s=o(t,i,n),a=Object.keys(s);r(null,a.map(function(e){return{caption:e,value:e,score:s[e],meta:"local"}}))}}),ace.define("ace/ext/language_tools",["require","exports","module","ace/snippets","ace/autocomplete","ace/config","ace/lib/lang","ace/autocomplete/util","ace/autocomplete/text_completer","ace/editor","ace/config"],function(e,t,i){"use strict";var n=e("../snippets").snippetManager,o=e("../autocomplete").Autocomplete,r=e("../config"),s=e("../lib/lang"),a=e("../autocomplete/util"),c=e("../autocomplete/text_completer"),l={getCompletions:function(e,t,i,n,o){if(t.$mode.completer)return t.$mode.completer.getCompletions(e,t,i,n,o);var r=e.session.getState(i.row),s=t.$mode.getCompletions(r,t,i,n);o(null,s)}},h={getCompletions:function(e,t,i,o,r){var s=n.snippetMap,a=[];n.getActiveScopes(e).forEach(function(e){var t=s[e]||[];for(var i=t.length;i--;){var n=t[i],o=n.name||n.tabTrigger;if(!o)continue;a.push({caption:o,snippet:n.content,meta:n.tabTrigger&&!n.name?n.tabTrigger+"⇥ ":"snippet",type:"snippet"})}},this),r(null,a)},getDocTooltip:function(e){"snippet"==e.type&&!e.docHTML&&(e.docHTML=["<b>",s.escapeHTML(e.caption),"</b>","<hr></hr>",s.escapeHTML(e.snippet)].join(""))}},p=[h,c,l];t.setCompleters=function(e){p.length=0,e&&p.push.apply(p,e)},t.addCompleter=function(e){p.push(e)},t.textCompleter=c,t.keyWordCompleter=l,t.snippetCompleter=h;var u={name:"expandSnippet",exec:function(e){return n.expandWithTab(e)},bindKey:"Tab"},d=function(e,t){f(t.session.$mode)},f=function(e){var t=e.$id;n.files||(n.files={}),g(t),e.modes&&e.modes.forEach(f)},g=function(e){if(!e||n.files[e])return;var t=e.replace("mode","snippets");n.files[e]={},r.loadModule(t,function(t){t&&(n.files[e]=t,!t.snippets&&t.snippetText&&(t.snippets=n.parseSnippetFile(t.snippetText)),n.register(t.snippets||[],t.scope),t.includeScopes&&(n.snippetMap[t.scope].includeScopes=t.includeScopes,t.includeScopes.forEach(function(e){g("ace/mode/"+e)})))})},m=function(e){var t=e.editor,i=t.completer&&t.completer.activated;if("backspace"===e.command.name)i&&!a.getCompletionPrefix(t)&&t.completer.detach();else if("insertstring"===e.command.name){var n=a.getCompletionPrefix(t);n&&!i&&(t.completer||(t.completer=new o),t.completer.autoInsert=!1,t.completer.showPopup(t))}},v=e("../editor").Editor;e("../config").defineOptions(v.prototype,"editor",{enableBasicAutocompletion:{set:function(e){e?(this.completers||(this.completers=Array.isArray(e)?e:p),this.commands.addCommand(o.startCommand)):this.commands.removeCommand(o.startCommand)},value:!1},enableLiveAutocompletion:{set:function(e){e?(this.completers||(this.completers=Array.isArray(e)?e:p),this.commands.on("afterExec",m)):this.commands.removeListener("afterExec",m)},value:!1},enableSnippets:{set:function(e){e?(this.commands.addCommand(u),this.on("changeMode",d),d(null,this)):(this.commands.removeCommand(u),this.off("changeMode",d))},value:!1}})});(function(){ace.require(["ace/ext/language_tools"],function(){})})()}})});