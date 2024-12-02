
# Report

## Task1:Create Alloy Model

| Test | Status | Reason |
| --- | --- | --- |
| check Number Of Signatures | ❌ Failed |  Number of signatures is less than 4  |
| check Number Of Fields Per Signature | ✅ Passed | - |
| check Inheritance | ❌ Failed |  No signature found that extebnds another signature. |
| check Number Of Facts | ❌ Failed |  Number of facts is less than 2  |
| check Number Of Predicates | ❌ Failed |  Number of predicates is less than 2  |
| check Number Of Run Commands | ❌ Failed |  Number of run commands is less than 2  |
| check Run Commands | ❌ Failed |  No unsatisfiable run command found  |
## Task2:Deadbury Mansion Puzzle

| Test | Status | Reason |
| --- | --- | --- |
| check Run Commands | ✅ Passed | - |
| check Killer Of Agatha | ✅ Passed | - |
| check Not Killer Of Agatha | ❌ Failed |  Model does not identify the correct killer of Agatha |
## Task3:Missing Predicates

| Test | Status | Reason |
| --- | --- | --- |
| check Inv1Some Trashed File | ❌ Failed |  A file in Trash should be a violation.  |
| check Inv1Some Files Exist | ✅ Passed | - |
| check Inv2AFile Not Deleted | ❌ Failed |  Some files are not deleted.  |
| check Inv2Possible To Delete Files | ✅ Passed | - |
| check Inv3AFile Is Deleted | ✅ Passed | - |
| check Inv3No Deleted Files | ❌ Failed |  Empty trash should be a violation.  |
| check Inv4AProtected File Is Deleted | ❌ Failed |  A protected file should not be in trash.  |
| check Inv4Some Deleted File | ✅ Passed | - |
| check Inv5An Unprotected And Not Deleted File | ❌ Failed |  No unprotected file should exist outside of Trash.  |
| check Inv5Some Deleted Unprotected File | ✅ Passed | - |
| check Inv6File Linking To Two Files | ❌ Failed |  A file cannot link to two files.  |
| check Inv6Files With No Links Possible | ✅ Passed | - |
| check Inv6Files With One Link Possible | ✅ Passed | - |
| check Inv7ALinked File Is Deleted | ❌ Failed |  A linked file should not be in trash.  |
| check Inv7Linke Files Exist | ✅ Passed | - |
| check Inv8Two Files With No Links | ✅ Passed | - |
| check Inv8Linke Files Exist | ❌ Failed |  Linked files should not be possible.  |
| check Inv9Consecutive Link Chain | ❌ Failed |  A link chain cannot be longer than one.  |
| check Inv9Linke Files Exist | ✅ Passed | - |
| check Inv10Deleted Link Without Deleted Target | ❌ Failed |  If a link is deleted, the target should be deleted too.  |
| check Inv10Linke And Deleted Files Exist | ✅ Passed | - |
