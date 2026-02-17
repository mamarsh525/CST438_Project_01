# CST438_Project_01
# VinylVault
#  Created by Maximilian Marshall, Metztli Herrera, Fernando Alvarado and Gael Romero


VinylVault is a music discovery and collection app built for CST 438.
The app allows users to search for musical artists, view their albums with cover art, 
and save favorite albums to a personal collection.

#Project 1 Vinyl Vault Android Application
Authors:
Maximilian Marshall
Gael Romero
Metzli
Fernando

Link to Repo:
https://github.com/mamarsh525/CST438_Project_01

Link to Video: https://drive.google.com/file/d/1p_v2dwaDz_wlgeV82gtrVLfaVT0B1U0u/view?usp=sharing

README:

# Project 01 Vinyl Vault Android Application

## Overview
This is an application that takes information from [Discogs](https://www.discogs.com/), the album and vinyl discography and cataloging site, and allows users to select releases from artists and add them to their collections.


We got styling help for this document from [this guide](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)

## Introduction

* How was communication managed
    * We communicated through discord, slack, text messages multiple times throughout the week, along with various meetings in person and online.
* How many stories/issues were initially considered
  30
* How many stories/issues were completed
  25
## Team Retrospective

### Maximilian Marshall

- [pull requests](https://github.com/mamarsh525/CST438_Project_01/pulls/mamarsh525)
- [issues](https://github.com/mamarsh525/CST438_Project_01/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3Amamarsh525)

#### What was your role / which stories did you work on
My role was organizing the project, fixing and identifying bugs, fixing gitignore problems, and reviewing and merging other prs. I initially added the login screen and home page, artist images, fixing test field issues, and getting a user added into the database so the login would work initially. I spent a lot of time working through github problems and other issues with the other team members.

+ What was the biggest challenge?
    + The biggest challenge for me was understanding how the android studio ecosystem worked. We used AI a lot, but I made it a point to not let it code without understanding what was happening.
+ Why was it a challenge?
    + This was a challenge, as the understanding was necessary when reviewing long PRs. Understanding how they worked, if there were any bugs, merge conflicts, etc, took a very long time.
    + How was the challenge addressed?
        + the challenge was addressed by prioritizing reading through documentation, asking AI to explain the purpose of various android studio, kotlin, and other project features, and going to office hours.
+ Favorite / most interesting part of this project
    + Being exposed to composable, coroutines, and the api implementation. I know that I will implement this login more in the future, so it was cool getting more familiar with the functionalities.
+ If you could do it over, what would you change?
    + I would have spent more organized time reading through android studio walkthroughs and documentation.
+ What is the most valuable thing you learned?
    + The most valuable thing I’ve learned is how to use github. Before this class I didn’t feel very comfortable with it, and while I still face confusion, I think I understand the process much more.

Fernando Alvarado
1. Fernando’s pull requests are https://github.com/mamarsh525/CST438_Project_01/pulls?q=is%3Apr+author%3AFernando-A831+is%3Aclosed

1. Fernando’s Github issues are https://github.com/mamarsh525/CST438_Project_01/issues?q=is%3Aissue%20state%3Aclosed%20assignee%3AFernando-A831


What was your role / which stories did you work on?
I first started working on our logout button and logout functionality, as well as making a logout test during the beginning of the project. I then started working on getting the app and user internet permissions/connectivity in order to start using the API. After that was finished up I then started working on the API connectivity, and setting up the basic API folder structure, adding the proper retrofit & GSON dependencies to convert the API data into a readable format for kotlin. The last implemented feature I worked on was making basic API queries and setting up retrofit client in order to get the Random Artist button working, showing a random artist on click. There was an unimplemented favorite feature as well that didn't make the final project as a teammate made a much more visually appealing  version with more functionality that we chose to go with instead of mine.

+ What was the biggest challenge?
    + getting started on the API, and figuring out how to get the API working with kotlin
+ Why was it a challenge?
    + Was a bit confused on how to set up the API file structure and using retrofit in order to make the calls to Discogs api, as well as converting the json data using GSON for kotlin
    + How was the challenge addressed?
    + I used the very detailed discogs documentation which showed all the methods to making queries to their database to pull the wanted data from, as well as looking at other discogs projects and how they solved their issues.
+ Favorite / most interesting part of this project
    + Finally getting the API fully implemented and being able to get an artist and image from the API to be displayed
+ If you could do it over, what would you change?
    + I wouldn't change much, but maybe spend more time on making a bigger variety of features for users to use that showcase the Discogs API.
+ What is the most valuable thing you learned?
    + To do our work on a timely manner, spread out our issues and Prs into easier to follow sizes, and to have documentation and comments for everything in the project no matter how small or simple the change or code




Metztli Herrera

Pull Requests:
https://github.com/mamarsh525/CST438_Project_01/pulls?q=is%3Apr+author%3Athatsnotmyjob+is%3Aclosed

GitHub Issues:
https://github.com/mamarsh525/CST438_Project_01/issues?q=is%3Aissue%20author%3Athatsnotmyjob

What was your role / which stories did you work on

I worked primarily on backend integration and core feature implementation. My main contributions included:

Designing and implementing the Room database schema

Setting up persistent user account storage

Implementing artist search functionality

Displaying an artist’s releases

Allowing users to add music to their personal collection

Creating the collection page UI to display saved albums in a grid layout

My work focused on connecting API data with local database storage to make the collection feature fully functional and persistent.

What was the biggest challenge?

I spent most of my time working on getting an artist’s releases to show up.

Why was it a challenge?

I went down a rabbit hole with the Discogs API and specifically how it returns data. To get everything showing up correctly multiple components had to work together correctly; the API request, the response parsing, and the UI updates. The response parsing specifically gave me the most trouble as I had to learn how to parse nested JSON responses with multiple levels of objects and arrays as well as filter duplicate releases.

Favorite / most interesting part of this project

Integrating the network requests behind the Discogs API calls and saving the responses to the database felt the most rewarding as I was seeing the accumulation of different types of computer science knowledge converge into one process.

If you could do it over, what would you change?

If I had to make this app again I would spend more time tinkering with the API in the beginning instead of setting up the database first. In hindsight it makes more sense to have a solid understanding of what type of data one will be working with before designing the schema. This way I could have avoided several revisions of the database.

What is the most valuable thing you learned?

This project was a great crash course in GitHub as well as the design/planning process behind software development. Before this I was overconfident in my ability to use GitHub and very quickly I learned how much I didn’t know. Every accidental merge to main and merge conflict were all great learning experiences for me and left me feeling much more comfortable with using GitHub.

Team Member name :  Gael Romero
a link to your pull requests :

Closed:
https://github.com/mamarsh525/CST438_Project_01/pulls?q=is%3Apr+is%3Aclosed+author%3AgRome0

Open:  https://github.com/mamarsh525/CST438_Project_01/pulls/gRome0
a link to your issues: https://github.com/mamarsh525/CST438_Project_01/issues?q=is%3Aissue%20assignee%3AgRome0

What was your role / which stories did you work on: I created the Login page and registration portion of the app making sure users passwords and username are registered properly and then added a favorites page and removal of a favorite and then created the readme.

What was the biggest challenge? - The biggest challenge was creating the handling pull request and getting used to GitHub again and making sure we weren’t having conflicts with one another.

Why was it a challenge? - It was a challenge because I didn’t want to create conflicts with someone else's work.

How was the challenge addressed? - The challenge was addressed by communicating with teammates on what each of us was doing to make sure to not interfere with each other's work.

Favorite / most interesting part of this project - The most interesting part was seeing how the API worked and using it in the app.

If you could do it over, what would you change? - I would have wanted to brush up on github before having started this project.

What is the most valuable thing you learned? - Learned how to use github better and more efficiently with a team.




## Conclusion

- How successful was the project? - I think the project was successful as we completed everything in the rubric required for the app. For the most part we achieved the core goal and functionality we initially planned for.

    - Think in terms of what you set out to do and what actually got done? - We were successfully able to use the Discogs API to search artists and see their releases. Although we had many more features in mind that didn’t make it to production. A big goal from the beginning that wasn’t added was integrating the Spotify API so users could look at their existing library to easily search artists.

- What was the largest victory? - The largest victory was getting the API to have the vinyl album covers to actually appear on the app from API.

- Final assessment of the project - The final assessment of the project taught us how to work together on github better and communicate on what steps we had to do in order to complete the project. 




