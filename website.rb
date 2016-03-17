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

@jobs = Array.new
@jobs << Job.new( "2015-Present", "UCSD Computer Science Department", "Tutor",
                 "Tutored an Introduction to Java course. Gained experience
                 leading discussion sections and a small study
                 group. Assisted students with programming questions during lab
                 hours or online. Graded programming
                 assignments, quizzes, and exams." )

@leaderships = Array.new
@leaderships << Job.new( "January 2016-Present", "Alpha Phi Omega", "Webmaster",
                        "Appointed to a position on the executive board to
                        manage the organization’s website, including bug fixes
                        and improvements. Dealt with member requests in a timely
                        manner. Headed a committee. Projects are
                        described below.", "leadership" )

@projects = Array.new
@projects << Project.new( "Alpha Phi Omega Website Features", "#", "image.jpg", 
                         "Forum and requirement tracker", @projects.count + 1 )

erb = ERB.new(File.open("#{__dir__}/website.html.erb").read, 0, '>')
puts erb.result binding
