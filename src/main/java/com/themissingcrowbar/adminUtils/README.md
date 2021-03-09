## New Warning system

Ive created a new way of doing warnings for the server, a decaying warning system.
It works by having several predefined WarnTypes, each having
their own weight and decay rate. When a user gets a warning
their total warn weight is added together, if it above 
a certin threshold a punishment is given. 
Each warnType has its own decay rate and the total weight
for a warning decreases over time using a decaying function.

`warnWeight*e^(decayrate*time)` time is days since warning.

### Definitions

<details>
    <summary>Warning</summary>
    A warning contains the following data, 
    warnId, date, warnType, user
</details>
<br>
<details>
    <summary>WarnTypes</summary>
    A warnType contains the following data,
    weight, name, description, decayRate
</details>