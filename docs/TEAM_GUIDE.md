# MamaCare Team Guide

Read this once before writing any code.

## 1. First-time setup
```bash
git clone https://github.com/Ve3-nus/Mama-Care.git
cd Mama-Care
git checkout develop
```
Open the folder in **Android Studio** and let Gradle sync. Run the app: you should see a Dashboard with
buttons and a bottom navigation bar. If that works, you are ready.

## 2. Your daily workflow
```bash
git checkout develop
git pull                                  # always start from the latest develop
git checkout -b feature/<your-task>       # e.g. feature/room-entities
# ... write code ...
git add .
git commit -m "feat: add Medication entity"
git push -u origin feature/<your-task>
```
Then on GitHub open a **Pull Request into `develop`** (not `main`) and tag Venus to review.

Rules:
1. Never commit to `main` or `develop` directly.
2. Small, frequent commits. Prefixes: `feat:`, `fix:`, `docs:`, `test:`, `refactor:`.
3. Pull `develop` into your branch before opening a PR (`git pull origin develop`).
4. Do not edit `nav_graph.xml`, `MainActivity.kt` or Gradle files without telling Venus
   (these cause merge conflicts). Ask for the change instead.
5. Never commit `local.properties`, `build/`, `.idea/` (already in `.gitignore`).

## 3. Who owns what
| Member | Branch examples | Package / folder |
|---|---|---|
| Venus | `feature/project-setup`, `docs/*` | root, `MainActivity`, `res/navigation` |
| Mapelu Joe, Abraham Kwikiriza | `feature/ui-layouts`, `feature/adapters` | `res/layout`, `ui/*` (layouts, adapters) |
| Boniface Mwangi | `feature/room-db` | `data/local` |
| Dollan James Ochele | `feature/repository-viewmodel` | `data/repository`, `ui/*/…ViewModel` |
| Brian Omoto | `feature/retrofit-api` | `data/remote` |
| Ryan | `feature/workmanager`, `feature/sos` | `worker`, `ui/sos` |

## 4. How navigation works
- `res/navigation/nav_graph.xml` lists every screen and the arrows between them.
- A list screen opens the form with an id: `MedicationListFragmentDirections.actionMedicationListToAddEdit(medicationId = id)`.
  `-1` means "new item".
- The form reads it with `private val args: AddEditMedicationFragmentArgs by navArgs()`.
- Stubs already exist for every screen. Replace the stub's contents; keep the class and layout names so navigation keeps working.

## 5. Data Binding note
Data Binding is enabled. Wrap a layout in `<layout>` to use it. If you need `@BindingAdapter`, tell Venus first
(it needs the kapt plugin).

## 6. Merge conflict? Do not panic
```bash
git checkout feature/<your-task>
git pull origin develop      # conflicts appear in files
# fix the files, remove the <<<<<<< ======= >>>>>>> markers
git add .
git commit -m "fix: resolve merge conflict with develop"
git push
```
