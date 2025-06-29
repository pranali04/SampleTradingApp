import useAuth from '../hooks/useAuth';

export async function apiFetch(path, options = {}) {
  const { authHeader } = useAuth();
  const res = await fetch(path, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...authHeader(),
    },
  });
  if (!res.ok) throw new Error(await res.text());
  return res.json();
}
