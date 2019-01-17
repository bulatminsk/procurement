# Procurement

Procurement Solution is a platform that allows companies needing to choose the best performer for their tender orders and to apply for a tender.
A user in the application could play any of three types of roles: tender owner, member of tender committee and applicant(proposal owner). User roles have following features.

## User, as a tender owner, could: 
* Register
* Create new company
* Update profile (if has no active tenders)
* Create a new tender on platform
* Define evaluation criteria for tender (until tender is published)
* Add user, which represents the same company to the tender committee. The tender committee members will made evaluations of all proposals received to the tender.
* Remove user from the tender committee
* Publish the tender (when evaluation criteria are defined and tender is not deleted)
* Delete the tender
* View placed tenders
* Choose tender
* Move to active tender
* View final evaluations of proposals
* Define tender winner (if tender is not deleted)

##  User, as an applicant (proposal owner), could: 
* Register
* Create new company
* Update profile (until proposal is placed)
* View all active tenders (tenders which are published and not deleted) and apply to any
* Prepare proposal for a tender
* Attach file to proposal
* Delete file from proposal
* Place proposal to tender
* View placed proposals

##  User, as a tender committee member, could: 
* Register
* Create new company
* Update profile
* View a list of tenders assigned to evaluate
* Choose tender
* Move to active tender
* View proposals to evaluate
* Move to active proposal
* View proposal
* Download file from proposal
* Evaluate proposal
* View evaluation of proposal, made by user

##  Technical description:
* Java 8;
* JavaEE: Servlet, JSP;
* Server / Servlet container: tomcat 8.5.35
* Data base: MySQL;
* JDBC;
* Logger: Log4J2;
* Tests: TestNG;
* Build tool: Maven.
