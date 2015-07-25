package model;

import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Key
import groovyx.gaelyk.datastore.Unindexed

@Entity(unindexed=false)
class Employee {
    @Key long id
    String name
    String designation
    
   
}