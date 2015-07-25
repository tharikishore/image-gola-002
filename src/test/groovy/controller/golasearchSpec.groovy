package controller
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import static imagegola.OfyService.ofy
import groovyx.gaelyk.spock.*
import imagegola.GolaImage
import imagegola.Google
import spock.lang.IgnoreRest

import com.google.appengine.api.datastore.*

class golasearchSpec extends ConventionalGaelykUnitSpec {

    def "the datastore is used from within the groovlet"() {
        given: "the initialised groovlet is invoked and data is persisted"
        groovletInstance.get()

        when: "the datastore is queried for data"
        def query = new Query("GolaImage")
        //query.addFilter("firstname", Query.FilterOperator.EQUAL, "Marco")
        def preparedQuery = datastore.prepare(query)
        def entities = preparedQuery.asList(withLimit(1))

        then: "the persisted data is found in the datastore"
        def img = entities[0]
        img.imgSrc == 'google'
    }
	
	
	def "save and retrieve data"(){
		when:
		def str ="""
'google', '1.url', '11', '131', '1.0'
'google', '2.url', '12', '132', '1.1'
'yahoo', '3.url', '13', '133', '1.2'
'yahoo', '4.url', '14', '134', '1.3'
'yahoo', '5.url', '15', '135', '1.4'
'yahoo', '6.url', '16', '136', '1.5'
'yahoo', '7.url', '17', '137', '1.6'
'yahoo', '8.url', '18', '138', '0.5'
'yahoo', '9.url', '19', '139', '0.6'
'yahoo', '10.url', '22', '140', '0.2'
'google', '11.url', '23', '141', '0.8'
"""
		str.split("\n").each{line ->
			def words = line.split()
			if(words?.size() == 5){
				def img = new Entity("GolaImage")
				img.imgSrc = words[0]
				img.imgUrl = words[1]
				img.height = words[2]
				img.width = words[3]
				img.aspectRatio = words[4]
				img.save()
			}
			println words
		}
		
		and:
			def query = new Query("GolaImage")
			query.addFilter("imgSrc", Query.FilterOperator.EQUAL, "google")
			def preparedQuery = datastore.prepare(query)
			def entities = preparedQuery.asList(withLimit(20))//withLimit(1)
	
		then:
			entities.size() == 8
		
	}

	def "search trial"(){
		when:
		ofy().save().entity(new GolaImage(imgSrc:'google', imgUrl:'1.url')).now()
		ofy().save().entity(new GolaImage(imgSrc:'google', imgUrl:'2.url')).now()
		def img = ofy().load().type(GolaImage.class).filter('imgUrl', '2.url').list();
		then:
		img.size() == 56
		
	}
	
	@IgnoreRest
	def ""(){
		when:
		Google google = new Google("Egg", 40);
		google.startSearch();
		while (!google.isProcessDone()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List entityList = google.entityList;

		then:
			entityList.size() > 0
		when:
			entityList.each{
				println it.properties
				if(Double.parseDouble(it.aspectRatio) > 1.0)
					it.aspectRatio = 'Portrait'
				else
					it.aspectRatio = 'Landscape'
				ofy().save().entity(it).now()
			}
		and:
			def lis = ofy().load().type(GolaImage.class).filter('aspectRatio','Landscape').list()
			println "*****************"
			lis.each{
				println it.properties
			}
		then:
			lis.size() == 8
	}
}

