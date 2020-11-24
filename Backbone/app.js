   var Student = Backbone.Model.extend();   //moder defined

   var student1 = new Student({       //model object 1
		 	name:"suryansh",
		 	rollNo:101,
		 	city:"pilibjit"
		 });

		 var student2 = new Student({  //modle objecct 2
		 	name:"anmol",
		 	rollNo:102,
		 	city:"bijnoe"
		 });

		 var student3 = new Student({   //model object
		 	name:"mohit",
		 	rollNo:103,
		 	city:"gujrat"
		 });

		 var MyCollection = Backbone.Collection.extend({  //collection defined
		 	model:Student
		 });

		 var myCollection = new MyCollection();        //collection object defined  can aslo pass student object inside this

		  //myCollection.add(student1);                      //or like this usig add function
		 // myCollection.add(student2);
		 myCollection.add([student1,student2]);
		 myCollection.add(student3,{at:1});              //to add objject at any place
		 //myCollection.unshift(student1);
		// myCollection.unshift([student1,student2]);        //in unshift the new object is added at the front index
		 //myCollection.push(student1);                  //push is same add
		 //myCollection.push([student1,student2]);

		 console.log(myCollection.toJSON());

		// myCollection.remove(student3);                 //to remove any object
		 myCollection.pop();                             //remove the last index object
		 myCollection.shift();                           //remove from first index                 
		 console.log(myCollection.toJSON());

		 var MyView = Backbone.View.extend({       //view defined
		 	collection:myCollection,
		 	initialize:function(){
		 		this.render();
		 	},
		 	render:function(){
		 		console.log("view created for the project");

		 	}
		 });

		// var myView = new MyView();