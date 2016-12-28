require 'erb'

class Course
  attr_accessor :title, :number, :id
  @title = ""
  @number = ""
  @id = 0

  def initialize( title, num, id )
    @title = title
    @number = num
    @id = id
  end
end

#also leadership, sorted by @type
class Job
  attr_accessor :date, :company, :position, :description, :type
  @date = ""
  @company = ""
  @position = ""
  @description = ""
  @type = ""

  def initialize( date, comp, position, des, type = "job" )
    @date = date
    @company = comp
    @position = position
    @description = des
    @type = type
  end
end

class Project
  attr_accessor :id, :link, :image, :title, :description
  @id = 0
  @link = ""
  @image = ""
  @title = ""
  @description = ""
  
  def initialize( title, l, img, des, id )
    @title = title
    @id = id
    @link = l
    @image = img
    @description = des
  end
end

@courses = Array.new
@courses << Course.new( "Introduction to Java", "CSE 11", @courses.count + 1)
@courses << Course.new( "Discrete Mathematics", "MATH 15A", @courses.count + 1)
@courses << Course.new( "Mathematics for Algorithms & Systems", "CSE 21", @courses.count + 1)
@courses << Course.new( "Basic Data Structures & OO Design", "CSE 12", @courses.count + 1)
@courses << Course.new( "Software Tools & Techniques Laboratory", "CSE 15L", @courses.count + 1)
@courses << Course.new( "Computer Organization & Systems Programming", "CSE 30", @courses.count + 1)
@courses << Course.new( "Advanced Data Structures", "CSE 100", @courses.count + 1)
@courses << Course.new( "Theory of Computability", "CSE 105", @courses.count + 1)
@courses << Course.new( "Software Engineering", "CSE 110", @courses.count + 1)
@courses << Course.new( "Components & Design Techniques for Digital Systems", "CSE 140", @courses.count + 1)
@courses << Course.new( "Digital Systems Laboratory", "CSE 140L", @courses.count + 1)

@jobs = Array.new

@jobs << Job.new( "June-September 2016", "Lockheed Martin", "Software
Engineering Intern", "Worked with a team on a plugin dedicated to integrating
two different systems and providing a graphical representation. Learned and
applied new technologies/methodologies including ReST, DDS, Java GUI, and
JMockit. Contributed to interface design, which was utilized in the plugin to
provide flexibility." )

@jobs << Job.new( "2015-Present", "UCSD Computer Science Department", "Tutor",
                 "Tutored an Introduction to Java course. Gained experience
                 leading discussion sections and a small study
                 group. Assisted students with programming questions during lab
                 hours or online. Graded programming
                 assignments, quizzes, and exams." )

@jobs << Job.new( "July 2015", "Digital Media Academy", "Teacher Assistant",
                  "Courses included Java Programming for Minecraft Modding and
                  Introduction to Programming with Scratch. Provided one-on-one
                  instruction to students in need. Led break-time activities and
                  supervised during lunch." )

@leaderships = Array.new
@leaderships << Job.new( "January 2016-Present", "Alpha Phi Omega", "Webmaster",
                        "Appointed to a position on the executive board to
                        manage the organization's website, including bug fixes
                        and improvements. Dealt with member requests in a timely
                        manner. Headed a committee. Projects are
                        described below.", "leadership" )

@leaderships << Job.new( "January-March 2015", "Alpha Phi Omega", "Finance Chair",
                         "Elected to my pledge class executive board. Led a committee to raise $1000+
                         through fundrasiers such as restaurant coupons, pie deliveries, and face pie-ing.",
                         "leadership" )

@projects = Array.new

@projects << Project.new( "Team Assassins (Ruby on Rails)", 
                          "http://team-assassins.herokuapp.com",
                          "images/assassins.png",
                          "An application to help an administrator manage a game of 
                          Assassins, from assigning targets to counting kills and 
                          tracking leaderboards",
                          @projects.count + 1 )

@projects << Project.new( "DebateGate (Ruby on Rails)", 
                         "https://debate-gate.herokuapp.com/",
                         "images/debate-gate.png", 
                         "An incomplete web application that helps to facilitate
                         online debates.", @projects.count + 1 )
                         
@projects << Project.new( "Requirement Tracker (Ruby on Rails)", "#",
                         "images/greensheet.png", "Help members record their
service hours and other requirements.", @projects.count + 1 )

@projects << Project.new( "Forum (Ruby on Rails)", "#", "images/forum.png",
                         "Help members record their service hours and other
                         requirements.", @projects.count + 1 )

@projects << Project.new( "Palace (HTML/CSS/JS)",
                         "http://matthew-chinn.github.io/palace",
                         "images/palace.jpg", "Implemented an online version of
                         a card game to practice web development. Created a
                         simple AI to play against the user. Code viewable on
                         Github.", @projects.count + 1 )

@projects << Project.new( "Workout Android App (Java/XML)",
                         "https://github.com/matthew-chinn/GymLog",
                         "images/gym1.jpg", "Allows the user, to record workout
                         data, view history, and send emails containing that
                         day's workout statistics.", @projects.count + 1 )

erb = ERB.new(File.open("#{__dir__}/website.html.erb").read, 0, '>')
puts erb.result binding
