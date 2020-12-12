# PetDB shell

PetDB Shell application.

# Query Commands

## Get < key >
Get the given input keys value.
## Set < key > < value >
Set the given key and value
## Delete < key >
Deletes the given key and value
## Count
Count the database keys

# Transaction Commands
## Begin
Begin a new transaction, can be invoked n times for child transactions.
## Rollback
Rollback the active transaction
## Commit 
Commits the transaction to the database
## End
Ends the active transaction

# Flush
Flushes the database

# Dump
Dump the database entries into a JSON or XML file.
