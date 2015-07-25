
get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/datetime", forward: "/datetime.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"

get "/search", forward: "/controller/golasearch.groovy"
post "/search", forward: "/controller/golasearch.groovy"

post "/list", forward: "/controller/golalist.groovy"
get "/list", forward: "/controller/golalist.groovy"